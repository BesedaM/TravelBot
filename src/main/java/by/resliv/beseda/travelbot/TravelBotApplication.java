package by.resliv.beseda.travelbot;


import by.resliv.beseda.travelbot.bot.TravelBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@EnableMongoRepositories(basePackages = "by.resliv.beseda.travelbot.repository")
@EnableWebMvc
@PropertySource("classpath:bot.properties")
@SpringBootApplication(scanBasePackages = {"by.resliv.beseda.travelbot.service", "by.resliv.beseda.travelbot.controller"})
public class TravelBotApplication {

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TravelBotApplication.class, args);
    }

    @Bean
    public TelegramBotsApi getApi(){
        return new TelegramBotsApi();
    }


    @Bean
    public TravelBot getTravelBot(TelegramBotsApi api) throws TelegramApiRequestException {
        String botName = environment.getProperty("bot_name");
        String token = environment.getProperty("telegram_api_token");
        TravelBot bot=new TravelBot();
        bot.setName(botName);
        bot.setToken(token);
        api.registerBot(bot);
        return bot;
    }


}
