package com.siliver.ch1.service;

public interface CreditSystemService {
    public int getUserCredit(int userId);
    public boolean addCedit(int userId,int score);
}
