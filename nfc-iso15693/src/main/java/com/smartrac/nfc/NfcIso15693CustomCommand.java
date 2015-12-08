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

public class NfcIso15693CustomCommand extends NfcIso15693Command
{
    public NfcIso15693CustomCommand(byte opcode, byte manufacturerCode) {
        super(opcode);
        this.manufacturerCode = manufacturerCode;
    }

    public NfcIso15693CustomCommand(byte opcode, byte manufacturerCode, byte[] parameters) {
        super(opcode, parameters);
        this.manufacturerCode = manufacturerCode;
    }

    @Override
    public NfcIso15693CustomCommand setAddressed(byte[] uid) {
        super.setAddressed(uid);
        return this;
    }

    @Override
    public NfcIso15693CustomCommand setNonAddressed() {
        super.setNonAddressed();
        return this;
    }

    @Override
    public NfcIso15693CustomCommand setSelected() {
        super.setSelected();
        return this;
    }

    @Override
    public NfcIso15693CustomCommand clearOptionFlag() {
        super.clearOptionFlag();
        return this;
    }

    @Override
    public NfcIso15693CustomCommand setOptionFlag() {
        super.setOptionFlag();
        return this;
    }

    @Override
    public NfcIso15693CustomCommand setRequestFlags(byte requestFlags)
    {
        this.requestFlags = requestFlags;
        return this;
    }

    @Override
    public byte[] toByteArray()
    {
        byte[] isoCommand;
        if ((NfcIso15693Flags.REQ_ADRESS == (requestFlags & NfcIso15693Flags.REQ_ADRESS)) &&
                (0 == (requestFlags & NfcIso15693Flags.REQ_INVENTORY))) {
            isoCommand = new byte[11 + parameters.length];
            System.arraycopy(uid, 0, isoCommand, 3, uid.length);
            System.arraycopy(parameters, 0, isoCommand, 3 + uid.length, parameters.length);
        }
        else {
            isoCommand = new byte[3 + parameters.length];
            System.arraycopy(parameters, 0, isoCommand, 3, parameters.length);
        }
        isoCommand[0] = requestFlags;
        isoCommand[1] = opcode;
        isoCommand[2] = manufacturerCode;
        return isoCommand;
    }

    private byte manufacturerCode;
}
