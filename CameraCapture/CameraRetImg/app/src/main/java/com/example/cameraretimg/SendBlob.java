package com.example.cameraretimg;
import android.os.AsyncTask;
import android.widget.TextView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.example.cameraretimg.MainActivity;
import java.util.UUID;

public class SendBlob extends AsyncTask<String, Void, Void> {
    private TextView view;
    private MainActivity act;

    public SendBlob(MainActivity act, TextView view) {
        this.view = view;
        this.act = act;
    }

    @Override
    protected Void doInBackground(String... arg0) {

        //act.printSampleStartInfo("BlobBasics");

        try {
            // Setup the cloud storage account.
            CloudStorageAccount account = CloudStorageAccount
                    .parse(MainActivity.storageConnectionString);

            // Create a blob service client
            CloudBlobClient blobClient = account.createCloudBlobClient();

            // Get a reference to a container
            // The container name must be lower case
            // Append a random UUID to the end of the container name so that
            // this sample can be run more than once in quick succession.
            CloudBlobContainer container = blobClient.getContainerReference("blobbasicscontainer"
                    + UUID.randomUUID().toString().replace("-", ""));

            // Create the container if it does not exist
            container.createIfNotExists();

            // Make the container public
            // Create a permissions object
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object
            containerPermissions
                    .setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            // Set the permissions on the container
            container.uploadPermissions(containerPermissions);

            // Upload 3 blobs
            // Get a reference to a blob in the container
            CloudBlockBlob blob1 = container
                    .getBlockBlobReference("blobbasicsblob1");

            //byte[] theByteArray = stringToConvert.getBytes();
            // Upload text to the blob
            blob1.uploadText("Send Data 1");
            //blob1.uploadFromByteArray(theByteArray,0,theByteArray.length);

            // Download the blob
            // For each item in the container
            for (ListBlobItem blobItem : container.listBlobs()) {
                // If the item is a blob, not a virtual directory
                if (blobItem instanceof CloudBlockBlob) {
                    // Download the text
                    CloudBlockBlob retrievedBlob = (CloudBlockBlob) blobItem;
                   // act.outputText(view, retrievedBlob.downloadText());
                }
            }

            // List the blobs in a container, loop over them and
            // output the URI of each of them
            for (ListBlobItem blobItem : container.listBlobs()) {
                //act.outputText(view, blobItem.getUri().toString());
            }

            // Delete the blobs
            blob1.deleteIfExists();

            // Delete the container
            container.deleteIfExists();
        } catch (Throwable t) {
            //act.printException(t);
        }

        //act.printSampleCompleteInfo("BlobBasics");

        return null;

    }
}
