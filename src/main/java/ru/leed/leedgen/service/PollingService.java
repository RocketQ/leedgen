package ru.leed.leedgen.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.leed.leedgen.entity.UserInfo;
import ru.leed.leedgen.properties.BotProperties;
import ru.leed.leedgen.repository.UserInfoRepository;


@Component
@RequiredArgsConstructor
public class PollingService extends TelegramLongPollingBot {

  private final BotProperties botProperties;
  private final UserInfoRepository userInfoRepository;
  private final Map<Long, UserInfo> userStates = new HashMap<>();


  @Override
  public String getBotUsername() {
    return botProperties.name();
  }

  @Override
  public String getBotToken() {
    return botProperties.token();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();

      handleIncomingMessage(text, chatId);
    }
  }


  private void handleIncomingMessage(String text, long chatId) {
    UserInfo user = userStates.getOrDefault(chatId, new UserInfo());

    if (text.equalsIgnoreCase("/start")) {
      sendMessage(chatId, "Введите ваше имя:");
      return; // Прерываем обработку и ожидаем следующего сообщения.
    }

    if (user.getFirstName() == null) {
      if (!text.equalsIgnoreCase("/start")) {
        user.setFirstName(text);
        sendMessage(chatId, "Введите вашу почту:");
      } else {
        sendMessage(chatId, "Имя не должно быть '/start', введите корректное имя:");
      }
    } else if (user.getMail() == null) {
      if (text.contains("@")) {
        user.setMail(text);
        sendMessage(chatId, "Введите ваш пол:");
      } else {
        sendMessage(chatId, "Введённый адрес некорректен. Введите вашу почту ещё раз:");
      }
    } else if (user.getSex() == null) {
      user.setSex(text);
      sendMessage(chatId, "Введите ваш телефон:");
    } else if (user.getPhoneNumber() == null) {
      user.setPhoneNumber(text);
      sendMessage(chatId, "Спасибо за регистрацию!");
      userInfoRepository.save(user);
      userStates.put(chatId, user); // Сохраняем и обновляем данные пользователя.
    }
  }




  private void sendMessage(long chatId, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText(text);
    try {
      execute(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

}
