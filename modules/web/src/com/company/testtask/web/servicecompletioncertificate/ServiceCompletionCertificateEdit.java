package com.company.testtask.web.servicecompletioncertificate;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.testtask.entity.ServiceCompletionCertificate;
import com.haulmont.cuba.gui.components.FileMultiUploadField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServiceCompletionCertificateEdit extends AbstractEditor<ServiceCompletionCertificate> {

    @Inject
    protected FileMultiUploadField fileMultiUpload;

    @Inject
    protected CollectionDatasource<FileDescriptor, UUID> fileDs;

    @Inject
    protected DataSupplier dataSupplier;

    @Inject
    protected FileUploadingAPI fileUploadingAPI;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        fileMultiUpload.addQueueUploadCompleteListener(() -> {
            List<FileDescriptor> files = new ArrayList<>();
            for (Map.Entry<UUID, String> entry : fileMultiUpload.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileDescriptor fd = fileUploadingAPI.getFileDescriptor(fileId, fileName);
                files.add(fd);
                // save file to FileStorage
                try {
                    fileUploadingAPI.putFileIntoStorage(fileId, fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }
                // save file descriptor to database
                dataSupplier.commit(fd);
            }
            getItem().setFile(files);
            showNotification("Uploaded files: " + fileMultiUpload.getUploadsMap().values(), NotificationType.HUMANIZED);
            fileMultiUpload.clearUploads();
            fileDs.refresh();
        });

        fileMultiUpload.addFileUploadErrorListener(event ->
                showNotification("File upload error", NotificationType.HUMANIZED));
    }
}