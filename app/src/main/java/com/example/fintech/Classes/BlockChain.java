package com.example.fintech.Classes;

import android.provider.ContactsContract;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class BlockChain {

    private ArrayList<Block> chain = new ArrayList<>();

    public BlockChain(){
        this.chain.add(createGenesisBlock());

    }

    private Block createGenesisBlock() {
        return new Block(0, new Timestamp(System.currentTimeMillis()), "Genesis block", "0");
    }
    private Block getLatestBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public void addBlock(Block newBlock) {

        newBlock.setPreviousHash(this.getLatestBlock().getHash());
        newBlock.setHash(Block.calculateHash(newBlock));
        this.chain.add(newBlock);
    }

    private boolean isChainValidate() {
        for (int i = 1; i < this.chain.size(); i++) {
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i-1);
            if (currentBlock.getHash() != Block.calculateHash(currentBlock)) {
                return false;
            }

            if (currentBlock.getPreviousHash() != previousBlock.getHash()) {
                return false;
            }

        }
        return true;
    }

    public ArrayList<Block> getChain() {
        return chain;
    }
}
