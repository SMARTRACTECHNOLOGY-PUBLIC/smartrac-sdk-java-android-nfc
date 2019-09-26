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

public class NfcIso15693Opcode
{
    // Mandantory commands
    public static final byte INVENTORY = 0x01;
    public static final byte STAY_QUIET = 0x02;

    // Optional commands
    public static final byte READ_SINGLE = 0x20;
    public static final byte WRITE_SINGLE = 0x21;
    public static final byte LOCK = 0x22;
    public static final byte READ_MULTIPLE = 0x23;
    public static final byte WRITE_MULTIPLE = 0x24;
    public static final byte SELECT = 0x25;
    public static final byte RESET_TO_READY = 0x26;
    public static final byte WRITE_AFI = 0x27;
    public static final byte LOCK_AFI = 0x28;
    public static final byte WRITE_DSFID = 0x29;
    public static final byte LOCK_DSFID = 0x2A;
    public static final byte GET_SYSTEM_INFORMATION = 0x2B;
    public static final byte GET_MULTIPLE_SECURITY = 0x2C;

    // Extended commands
    public static final byte EXT_READ_SINGLE = 0x30;
    public static final byte EXT_WRITE_SINGLE = 0x31;
    public static final byte EXT_LOCK = 0x32;
    public static final byte EXT_READ_MULTIPLE = 0x33;
    public static final byte EXT_WRITE_MULTIPLE = 0x34;
    public static final byte EXT_GET_MULTIPLE_SECURITY = 0x3C;
}
