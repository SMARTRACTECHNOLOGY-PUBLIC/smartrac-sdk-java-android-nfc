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

import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.nfc.tech.TagTechnology;

import java.io.IOException;

public class NfcIso15693 implements TagTechnology {

    // Static constructor using the android.nfc.Tag object
    public static NfcIso15693 get(Tag tag) {
        return new NfcIso15693(tag);
    }

    public NfcIso15693(Tag tag) {
        nfcv = NfcV.get(tag);
        uid = tag.getId();
        maxTranscieveLength = nfcv.getMaxTransceiveLength();
    }

    public void connect() throws IOException {
        nfcv.connect();
    }

    public void close() throws IOException {
        nfcv.close();
    }
    
    public int getMaxTransceiveLength() {
        return maxTranscieveLength;
    }
    
    public Tag getTag() {
        return nfcv.getTag();
    }
    
    public boolean isConnected() {
        return nfcv.isConnected();
    }

    public byte[] readSingleBlock(int block) {
        byte[] param = new byte[1];
        param[0] = (byte)block;
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.READ_SINGLE, param).setAddressed(uid).toByteArray());
            if (resp.length > 1) {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    byte[] ret = new byte[resp.length - 1];
                    System.arraycopy(resp, 1, ret, 0, ret.length);
                    return ret;
                }
            }
            return null;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    public boolean writeSingleBlock(int block, byte[] data) {
        byte[] param = new byte[1 + data.length];
        param[0] = (byte)block;
        System.arraycopy(data, 0, param, 1, data.length);
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.WRITE_SINGLE, param).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public boolean lockBlock(int block) {
        byte[] param = new byte[1];
        param[0] = (byte)block;
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.LOCK, param).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public byte[] readMultipleBlocks(int startBlock, int numBlocks) {
        byte[] param = new byte[2];
        param[0] = (byte)startBlock;
        param[1] = (byte)(numBlocks - 1);
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.READ_MULTIPLE, param).setAddressed(uid).toByteArray());
            if (resp.length > 1) {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    byte[] ret = new byte[resp.length - 1];
                    System.arraycopy(resp, 1, ret, 0, ret.length);
                    return ret;
                }
            }
            return null;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    public boolean writeMultipleBlocks(int startBlock, int numBlocks, byte[] data) {
        byte[] param = new byte[2 + data.length];
        param[0] = (byte)startBlock;
        param[1] = (byte)(numBlocks - 1);
        System.arraycopy(data, 0, param, 2, data.length);
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.WRITE_MULTIPLE, param).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public boolean writeAFI(byte afi) {
        byte[] param = new byte[1];
        param[0] = afi;
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.WRITE_AFI, param).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public boolean lockAFI() {
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.LOCK_AFI).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public boolean writeDSFID(byte dsfid) {
        byte[] param = new byte[1];
        param[0] = dsfid;
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.WRITE_DSFID, param).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public boolean lockDSFID() {
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.LOCK_DSFID).setAddressed(uid).toByteArray());
            if (resp.length == 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    return true;
                }
            }
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public NfcIso15693SystemInformation getSystemInformation() {
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.GET_SYSTEM_INFORMATION).setAddressed(uid).toByteArray());
            if (resp.length > 1)
            {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    byte[] sysInfo = new byte[resp.length - 1];
                    System.arraycopy(resp, 1, sysInfo, 0, sysInfo.length);
                    return NfcIso15693SystemInformation.fromByteArray(sysInfo);
                }
            }
            return null;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    public byte[] getMultipleBlockSecurityStatus(int startBlock, int numBlocks) {
        byte[] param = new byte[2];
        param[0] = (byte)startBlock;
        param[1] = (byte)(numBlocks - 1);
        try {
            byte[] resp = nfcv.transceive(
                    new NfcIso15693Command(NfcIso15693Opcode.GET_MULTIPLE_SECURITY, param)
                            .setAddressed(uid).toByteArray());
            if (resp.length > 1) {
                if (resp[0] == NfcIso15693Flags.RESP_OK)
                {
                    byte[] ret = new byte[resp.length - 1];
                    System.arraycopy(resp, 1, ret, 0, ret.length);
                    return ret;
                }
            }
            return null;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    public byte[] transmitCustomCommand(NfcIso15693CustomCommand cmd) {
    	try {
    		return nfcv.transceive(cmd.toByteArray());
    	}
        catch (IOException ex)
        {
            return null;
        }
    }

    private byte[] uid;
    private NfcV nfcv;
    private int maxTranscieveLength;
}
