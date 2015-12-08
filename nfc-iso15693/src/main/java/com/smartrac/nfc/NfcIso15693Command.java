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

public class NfcIso15693Command
{
    public NfcIso15693Command(byte opcode) {
        this.requestFlags = NfcIso15693Flags.REQ_DATARATE_HIGH;
        this.opcode = opcode;
        this.parameters = new byte[0];
        this.uid = null;
    }

    public NfcIso15693Command(byte opcode, byte[] parameters) {
        this.requestFlags = NfcIso15693Flags.REQ_DATARATE_HIGH;
        this.opcode = opcode;
        this.parameters = parameters.clone();
        this.uid = null;
    }

    public NfcIso15693Command setAddressed(byte[] uid) {
        this.requestFlags &= ~NfcIso15693Flags.REQ_SELECT;
        this.requestFlags |= NfcIso15693Flags.REQ_ADRESS;
        this.uid = uid.clone();
        return this;
    }

    public NfcIso15693Command setNonAddressed() {
        this.requestFlags &= ~(NfcIso15693Flags.REQ_SELECT | NfcIso15693Flags.REQ_ADRESS);
        this.uid = null;
        return this;
    }

    public NfcIso15693Command setSelected() {
        this.requestFlags &= ~NfcIso15693Flags.REQ_ADRESS;
        this.requestFlags |= NfcIso15693Flags.REQ_SELECT;
        this.uid = null;
        return this;
    }

    public NfcIso15693Command clearOptionFlag() {
        this.requestFlags &= ~NfcIso15693Flags.REQ_OPTION;
        return this;
    }

    public NfcIso15693Command setOptionFlag() {
        this.requestFlags |= NfcIso15693Flags.REQ_OPTION;
        return this;
    }

    public NfcIso15693Command setRequestFlags(byte requestFlags)
    {
        this.requestFlags = requestFlags;
        return this;
    }

    public byte[] toByteArray()
    {
        byte[] isoCommand;
        if ((NfcIso15693Flags.REQ_ADRESS == (requestFlags & NfcIso15693Flags.REQ_ADRESS)) &&
                (0 == (requestFlags & NfcIso15693Flags.REQ_INVENTORY))) {
            isoCommand = new byte[10 + parameters.length];
            System.arraycopy(uid, 0, isoCommand, 2, uid.length);
            System.arraycopy(parameters, 0, isoCommand, 2 + uid.length, parameters.length);
        }
        else {
            isoCommand = new byte[2 + parameters.length];
            System.arraycopy(parameters, 0, isoCommand, 2, parameters.length);
        }
        isoCommand[0] = requestFlags;
        isoCommand[1] = opcode;
        return isoCommand;
    }

    protected byte requestFlags;
    protected byte opcode;
    protected byte[] parameters;
    protected byte[] uid;
}
