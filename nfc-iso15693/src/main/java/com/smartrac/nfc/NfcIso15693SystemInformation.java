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

public class NfcIso15693SystemInformation
{
    public static NfcIso15693SystemInformation fromByteArray(byte[] getSysinfoResponse) {
        try {
            return new NfcIso15693SystemInformation(getSysinfoResponse);
        }
        catch (IllegalArgumentException exArg) {
            return null;
        }
    }

    public NfcIso15693SystemInformation(byte[] getSysinfoResponse) {
        if (getSysinfoResponse == null)
            throw new IllegalArgumentException();
        if (getSysinfoResponse.length < 9)
            throw new IllegalArgumentException();

        infoFlags = getSysinfoResponse[0];
        int expectedLength = 9;
        if (FLAG_DSFID == (infoFlags & FLAG_DSFID))
            expectedLength += 1;
        if (FLAG_AFI == (infoFlags & FLAG_AFI))
            expectedLength += 1;
        if (FLAG_MEMSIZE == (infoFlags & FLAG_MEMSIZE))
            expectedLength += 2;
        if (FLAG_ICREF == (infoFlags & FLAG_ICREF))
            expectedLength += 1;
        if (getSysinfoResponse.length != expectedLength)
            throw new IllegalArgumentException();

        uid = new byte[8];
        System.arraycopy(getSysinfoResponse, 1, uid, 0, uid.length);

        int ptr = 9;
        if (FLAG_DSFID == (infoFlags & FLAG_DSFID)) {
            dsfid = getSysinfoResponse[ptr];
            ptr++;
        }
        else
            dsfid = 0;
        if (FLAG_AFI == (infoFlags & FLAG_AFI)) {
            afi = getSysinfoResponse[ptr];
            ptr++;
        }
        else
            afi = 0;
        if (FLAG_MEMSIZE == (infoFlags & FLAG_MEMSIZE)) {
            numBlocks = getSysinfoResponse[ptr] + 1;
            blockSize = getSysinfoResponse[ptr+1] + 1;
            ptr+=2;
        }
        else {
            numBlocks = 0;
            blockSize = 0;
        }
        if (FLAG_ICREF == (infoFlags & FLAG_ICREF)) {
            icRef = getSysinfoResponse[ptr];
        }
        else
            icRef = 0;
    }

    public byte getDSFID() {
        return dsfid;
    }

    public byte getAFI() {
        return afi;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public byte getIcRef() {
        return icRef;
    }

    static final byte FLAG_DSFID = 0x01;
    static final byte FLAG_AFI = 0x02;
    static final byte FLAG_MEMSIZE = 0x04;
    static final byte FLAG_ICREF = 0x08;

    private byte infoFlags;
    private byte[] uid;
    private byte dsfid;
    private byte afi;
    private int blockSize;
    private int numBlocks;
    private byte icRef;
}
