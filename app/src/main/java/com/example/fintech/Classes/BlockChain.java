package com.example.fintech.Classes;

import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class BlockChain {

    private ArrayList<Block> chain = new ArrayList<>();
    private int miningReward;
    private int difficulty;
   // private ArrayList<Transaction> pendingTransaction = new ArrayList<>();

    public BlockChain(){
        this.chain.add(createGenesisBlock());
        this.difficulty = 2;
        this.miningReward = 100;
    }

    private Block createGenesisBlock() {
        return new Block(new Timestamp(System.currentTimeMillis()), "Genesis block", "0");
    }

    private Block getLatestBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.setPreviousHash(this.getLatestBlock().getHash());
        newBlock.setHash(Block.calculateHash(newBlock));
        this.chain.add(newBlock);
    }

    public boolean isChainValidate(ArrayList<Transaction> pendingTransaction) {
        for (int i = 1; i < this.chain.size(); i++) {
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i-1);
            if (!currentBlock.getTransaction().get(pendingTransaction.size()-1).getHash().equals(Transaction.calculateHash(currentBlock.getTransaction().get(pendingTransaction.size()-1)))) {
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

    public void miningPendingTransaction(String miningRewardAddress, ArrayList<Transaction> pendingTransaction) {
        Transaction rewardTx = new Transaction("REWARD", miningRewardAddress, this.miningReward);
        pendingTransaction.add(rewardTx);
        Block block = new Block(new Timestamp(System.currentTimeMillis()), pendingTransaction, this.getLatestBlock().getHash());
        block.mineBlock(this.difficulty);
        Log.d("johny","Block successfully mined");
        this.chain.add(block);
        Log.d("johny", "is chain valid? " + this.isChainValidate(pendingTransaction));
       // Log.d("johny", "the chain is " + this.getChain());
    }

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

}
