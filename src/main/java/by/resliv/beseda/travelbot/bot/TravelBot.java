package by.resliv.beseda.travelbot.bot;

import by.resliv.beseda.travelbot.model.City;
import by.resliv.beseda.travelbot.repository.CityRepositoryInterface;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

import static by.resliv.beseda.travelbot.bot.BotMessages.*;

@Setter
@Getter
public class TravelBot extends TelegramLongPollingBot {

    private static Logger error_logger = LogManager.getLogger("ERROR_LOGGER");
    private static Logger debug_logger = LogManager.getLogger("DEBUG_LOGGER");

    private String name;
    private String token;

    @Autowired
    private CityRepositoryInterface cityRepository;

    public TravelBot() {
    }

    public TravelBot(DefaultBotOptions opts) {
        super(opts);
    }


    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText().toLowerCase();
        try {
            sendAnswer(chatId, text);
        } catch (TelegramApiException | InterruptedException e) {
            error_logger.error(e);
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private synchronized void sendAnswer(String chatId, String text) throws TelegramApiException, InterruptedException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);

        if (text.equals(START_MESSAGE)) {
            wait(1000);
            sendMessage.setText(START_MESSAGE_RESPONSE);
            execute(sendMessage);
        } else {
            City city = cityRepository.findByName(text);
            wait(2000);
            if (city != null) {
                List<String> textList = city.getText();
                for (String textPart : textList) {
                    sendMessage.setText(textPart);
                    execute(sendMessage);
                    wait(1200);
                }
            } else {
                sendMessage.setText(UNKNOWN_CITY);
                execute(sendMessage);
            }
        }


    }


}
