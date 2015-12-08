package com.smartrac.nfc;

/*
 * *#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*
 * SMARTRAC SDK for Android NFC ISO15693
 * ===============================================================================
 * Copyright (C) 2015 Smartrac Technology Fletcher, Inc.
 * ===============================================================================
 * SMARTRAC SDK
 * (C) Copyright 2015, Smartrac Technology Fletcher, Inc.
 * 267 Cane Creek Rd, Fletcher, NC, 28732, USA
 * All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#
 */

public class NfcIso15693Flags
{
    // ISO 15693 Request Flags
    public static final byte REQ_SUBCARRIER_TWO = 0x01;
    public static final byte REQ_DATARATE_HIGH = 0x02;
    public static final byte REQ_INVENTORY = 0x04;
    public static final byte REQ_PROT_EXTENSION = 0x08;

    // ISO 15693 Flags to be used when REQ_INVENTORY is cleared
    public static final byte REQ_SELECT = 0x10;
    public static final byte REQ_ADRESS = 0x20;
    public static final byte REQ_OPTION = 0x40;

    // ISO 15693 Flags to be used when REQ_INVENTORY is set
    public static final byte REQ_INV_AFI = 0x10;
    public static final byte REQ_INV_ONETIMESLOT = 0x20;
    public static final byte REQ_INV_OPTION = REQ_OPTION;

    // ISO 15693 Response Flags
    public static final byte RESP_OK = 0x00;
    public static final byte RESP_ERROR = 0x01;
    public static final byte RESP_EXTENSION = 0x08;
}
