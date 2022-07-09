package com.example.fintech.Classes;

import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class BlockChain {

    private ArrayList<Block> chain = new ArrayList<>();
    private int miningReward;
    private int difficulty;
   // private ArrayList<Transaction> pendingTransaction = new ArrayList<>();

    public BlockChain(ArrayList<Block> chain){
        this.chain = chain;
        this.difficulty = 2;
        this.miningReward = 100;
    }


    public BlockChain(){
        this.chain.add(createGenesisBlock());
        this.difficulty = 2;
        this.miningReward = 100;
    }

    private Block createGenesisBlock() {
        return new Block(new Date(System.currentTimeMillis()), "Genesis block", "0");
    }

    private Block getLatestBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public void addBlock(Block newBlock) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        newBlock.setPreviousHash(this.getLatestBlock().getHash());
        newBlock.setHash(Block.calculateHash(newBlock));
        this.chain.add(newBlock);
    }

    public boolean isChainValidate(ArrayList<Transaction> pendingTransaction,byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        for (int i = 1; i < this.chain.size(); i++) {
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i-1);
            if (!currentBlock.getTransaction().get(pendingTransaction.size()-1).getHash().equals(Transaction.calculateHash(currentBlock.getTransaction().get(pendingTransaction.size()-1),privateKey))) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Block> getChain() {
        return chain;
    }

    public void miningPendingTransaction(int id, String miningRewardAddress, ArrayList<Transaction> pendingTransaction, String name, Date timestamp, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        Transaction rewardTx = new Transaction(id, "REWARD", miningRewardAddress, pendingTransaction.get(pendingTransaction.size()-1).getAmount(), true, name, timestamp, privateKey, false);

        pendingTransaction.add(rewardTx);
//        signTransaction(pendingTransaction);

        Block block = new Block(new Timestamp(System.currentTimeMillis()), pendingTransaction, this.getLatestBlock().getHash());
        block.mineBlock(this.difficulty);
        Log.d("johny","Block successfully mined");
        this.chain.add(block);
        Log.d("johny", "is chain valid? " + this.isChainValidate(pendingTransaction, privateKey));
       // Log.d("johny", "the chain is " + this.getChain());
    }

//    private void signTransaction(ArrayList<Transaction> pendingTransaction) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//        //Creating KeyPair generator object
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
//
//        //Initializing the key pair generator
//        keyPairGen.initialize(2048);
//
//        //Generate the pair of keys
//        KeyPair pair = keyPairGen.generateKeyPair();
//
//        //Getting the private key from the key pair
//        PrivateKey privKey = pair.getPrivate();
//
//        //Creating a Signature object
//        Signature sign = Signature.getInstance("SHA256withDSA");
//
//        //Initialize the signature
//        sign.initSign(privKey);
//
//        for (int i=0; i<pendingTransaction.size(); i++){
//
//
//            byte[] bytes = "msg".getBytes();
//
//            //Adding data to the signature
//            sign.update(bytes);
//
//            //Calculating the signature
//            byte[] signature = sign.sign();
//            pendingTransaction.get(i).set
//
//        }
//
//
//    }

    public void createTransaction(Transaction transaction, ArrayList<Transaction> pendingTransaction) {
        pendingTransaction.add(transaction);
    }

    public int getBalanceOfAddress(String address) {
        int balance = 0;
        for (Block block : this.chain) {
             for (Transaction trans : block.getTransaction()) {
                if (trans.getFromAddress().equals(address)) {
                    balance -= trans.getAmount();
                }
                if (trans.getToAddress().equals(address)) {
                    balance += trans.getAmount();
                }
            }
        }
        return balance;
    }

    public int getMiningReward() {
        return miningReward;
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return "BlockChain{" +
                "chain=" + chain +
                ", miningReward=" + miningReward +
                ", difficulty=" + difficulty +
                '}';
    }
}
