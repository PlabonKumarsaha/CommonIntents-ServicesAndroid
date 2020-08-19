## Various Services for Android

I have taken an Edit text that will take differnttypes of string data. There are 6 buttons which will perform different actions.

1.Web - the text will work as a URL and take the link to a web browser.
2.Email - the text will take the mail adress and take it to mail app
3.Dail - will take the number to the phone dailer
4.Call - will directly makea call.
5.SMS - redirect to SMS option
6.Maps - will open the maps.

## Web browsing button

1. Look for Web browser in the followg link :
https://developer.android.com/guide/components/intents-common

following code sniipet hepls to go to a browser after a string is inputted.

String url = et_data.getText().toString(); //takes the input string
                if(url!=null) {

                if(!url.startsWith("http://") || !url.startsWith("https://")){ //the string has to start with http or https request
                    url ="http://"+ url;
                }

                    Uri webpage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                    //the folllwoing thing will ask for browser prefernce
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

2. For sending mail without any attachments or only for opening mail intent :

   public void composeEmail(String[] addresses, String subject) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

otherwise for other apps do this snippet :

 public void composeEmail(String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

3. Dail : only takes the inputted number to the number typing location:

   String phoneNumber = et_data.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);

4. Call : direct phone call with the number 

add : <uses-permission android:name="android.permission.CALL_PHONE" /> in maifest. We have to take permission until the user denies it..to do so we take the following code


    //for calling permission
    private void onCallBtnClick(){
        if(Build.VERSION.SDK_INT <23){
            callPhoneNumber(et_data.getText().toString());
        }else{
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                callPhoneNumber(et_data.getText().toString());
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, 9);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            callPhoneNumber(et_data.getText().toString());
        }else {
            Toast.makeText(MainActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

Now the callPhone number calls the intent to call the phone no:

public void callPhoneNumber(String phoneNumber) {
       /* Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phoneNumber));
            this.startActivity(callIntent);
        }else{
            Toast.makeText(MainActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


5.  Send SMS : 
                String message ="User enters a messgae"; // the message 
                String phoneNo = et_data.getText().toString(); //phone no
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+phoneNo));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", message);
               // intent.putExtra(Intent.EXTRA_STREAM, attachment);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

6.  MAP : show location on map

the button click will handle the function like this :
<br>
                 String mapsQuery = "geo:47.6,-122?q="+et_data.getText().toString(); //takes a area name as an input for this.<br>
                 Uri geoLocation = Uri.parse(mapsQuery);
                 showMap(geoLocation);

### map functionality... This will open the map with a the location
//the function takes URI as a parameter <br>
     public void showMap(Uri geoLocation) { <br>
    Intent intent = new Intent(Intent.ACTION_VIEW);  <br>
    intent.setData(geoLocation);   <br>
    if (intent.resolveActivity(getPackageManager()) != null) { <br>
        startActivity(intent); <br>
    } <br>
}

