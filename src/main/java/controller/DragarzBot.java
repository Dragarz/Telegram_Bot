package controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.Util;

import javax.imageio.ImageIO;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DragarzBot extends TelegramLongPollingBot {

    private static final String FILENAME = "./Token.txt";
    Util token = null;

    public DragarzBot() {
        token = new Util(FILENAME);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());


            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(update.getMessage().hasPhoto()){
            downloadPhotoByFilePath(getFilePath(getPhoto(update)));
        }

    }

    @Override
    public String getBotUsername() {
        return token.getUserName();
    }

    @Override
    public String getBotToken() {
        return token.getToken();
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public PhotoSize getPhoto(Update update) {
        // Check that the update contains a message and the message has a photo
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // When receiving a photo, you usually get different sizes of it
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // We fetch the bigger photo
            return photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
        }

        // Return null if not found
        return null;
    }

    public String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.getFilePath() != null) { // If the file_path is already present, we are done!
            return photo.getFilePath();
        } else { // If not, let find it
            // We create a GetFile method and set the file_id from the photo
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                // We execute the method using AbsSender::execute method.
                File file = execute(getFileMethod);
                // We now have the file_path
                return file.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null; // Just in case
    }

    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            System.out.println(filePath);
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }
}

