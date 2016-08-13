package com.apps.phantom.rd.database;

/**
 * Created by Phantom on 8/11/2016.
 */
public class RandDDbSchema {
    public static final class RandDTable {
        public static final String NAME = "userid";


        public static final class Cols {
            public static final String ID = "id";
            public static final String PREMIUM = "premium";
            public static final String DEDUCTIBLE = "deductible";
            public static final String REMAINING = "remaining";
        }
    }
}