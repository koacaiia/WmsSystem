package com.koaca.wmssystem;

import android.provider.BaseColumns;

public class MemoContract {
    private MemoContract(){}
    public static class MemoEntry implements BaseColumns{
        public static final String TABLE_NAME="Wms";
        public static final String CARGO="cargo";
        public static final int OUTSOURCING=1;
        public static final String EQUIP="equip";
        public static final String ETC="etc";
    }
}
