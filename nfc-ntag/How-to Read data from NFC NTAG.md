# How-to: Read data from NFC NTAG in Android

Note: **DRAFT** - under construction

## Prerequisites

 - NFC enabled Android Device
 - Android development environment

## Enable NFC in the Android app

To be able to receive and consume NFC events, it is first required to enable NFC in the app and register for the [ACTION_TECH_DISCOVERED](http://developer.android.com/reference/android/nfc/NfcAdapter.html#ACTION_TECH_DISCOVERED "ACTION_TECH_DISCOVERED") event.

A very good guide is available on [https://developer.android.com/guide/topics/connectivity/nfc/nfc.html](https://developer.android.com/guide/topics/connectivity/nfc/nfc.html "NFC on Android Developer")

The following examples assume to have the NFC permission enabled and the basic NFC intent handler implemented in the app's activity.

## Variant A: access all tag functions on low-level by ISO protocol

Basically it is required to modify the onNewIntent function in the NFC activity to retrieve the Tag object:



		public void onNewIntent(Intent intent) {
		    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
		        NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
		        NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
		            // Retrieve Tag ID
		            byte[] uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		            // Retrieve Tag object
		            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		            if (tag != null) {
		                // Check if the tag supports NfcA/ISO14443A
		                boolean bIso14443A = false;
		                String sTech = tag.getTechList();
		                while (int i<sTech.length && ! bIso14443A) {
		                    if (sTech[i].equals("android.nfc.tech.NfcA")) {
		                        bIso14443A = true;
		                    }
		                }
		                if (bIso14443A)
		                {
		                // Do actions on the NTAG
		                try {
		                    NfcA nfca = NfcA.get(tag);
		                    nfca.connect();
		                    // add ISO request(s) here
		                    byte[] response = nfca.transcieve(...);
		                    nfca.close();
		                }
		                catch (IOException e) {
		                    // handle NFC comm error...
		                }
		            }
		        }
		    }
		}

The disadvantage of the method is that the programmer needs to know the ISO command set of the NXP NTAG and implement the required commands in transcieve(...).

## Variant B: Use NfcNtag API

The NfcNtag API is available as Maven Project in [https://github.com/SMARTRACTECHNOLOGY-PUBLIC/smartrac-sdk-java-android-nfc](https://github.com/SMARTRACTECHNOLOGY-PUBLIC/smartrac-sdk-java-android-nfc "NfcNtag API on GitHub") .

The API includes all functions of the NXP NTAG and allows easy integration of NTAG into Android.

Example:

		import com.smartrac.nfc.NfcNtag;
		 
		public void onNewIntent(Intent intent) {
		    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
		        NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
		        NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
		            // Retrieve Tag ID
		            byte[] uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		            // Retrieve Tag object
		            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		            if (tag != null) {
		                // Check if the tag supports NfcA/ISO14443A
		                boolean bIso14443A = false;
		                String sTech = tag.getTechList();
		                while (int i<sTech.length && ! bIso14443A) {
		                    if (sTech[i].equals("android.nfc.tech.NfcA")) {
		                        bIso14443A = true;
		                    }
		                }
		                if (bIso14443A)
		                {
		                // Do actions on the NTAG
		                try {
		                    NfcNtag ntag = NfcNtag.get(tag);
		                    ntag.connect();
		                    // read user memory blocks 4 to 15
		                    byte[] data = ntag.fastRead(0x04, 0x0F);
		                    ntag.close();
		                }
		                catch (IOException e) {
		                    // handle NFC comm error...
		                }
		            }
		        }
		    }
		}