package controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.Util;
import java.io.*;



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
        if (update.getMessage().hasPhoto()) {
            try {
                PictureController control = new PictureController(update);
                FileInputStream stream = new FileInputStream(control.getFile());
                StringBuilder fileName = new StringBuilder();
                fileName.append("./").append(control.getPath());
                FileOutputStream outputStream = new FileOutputStream(fileName.toString());
                while (stream.available() > 0) {
                    outputStream.write(stream.read());
                }
                stream.close();
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException j) {
            }
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


}

