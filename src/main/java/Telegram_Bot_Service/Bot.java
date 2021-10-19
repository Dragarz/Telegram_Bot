package Telegram_Bot_Service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private String userName = "DragarzBot";
    private String botToken = "2097087434:AAGVU_sY08KbqtoaNEmDAV0wtaEUwhH6OLw";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        // TODO
    }

    @Override
    public String getBotUsername() {
        // TODO
        return userName;
    }

    @Override
    public String getBotToken() {
        // TODO
        return botToken;
    }
}
