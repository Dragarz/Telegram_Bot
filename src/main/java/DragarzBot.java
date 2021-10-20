import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class DragarzBot extends TelegramLongPollingBot {
    private static final String FILENAME = "./Token.txt";
    TokenReader token = null;
    DragarzBot(){
        token = new TokenReader(FILENAME);
    }
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText("Hello World");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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

