package com.sdacademy.webapp.auth.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sdacademy.webapp.auth.model.Message;
import com.sdacademy.webapp.auth.repository.MessagesRepository;

@RunWith(MockitoJUnitRunner.class)
public class MessagesServiceTest {

  @Mock
  private MessageSender messageSender;

  @Mock
  private MessagesRepository messagesRepository;

  @Mock
  private MessagesIdGenerator messagesIdGenerator;

  @InjectMocks
  private MessagesService messagesService;

  @Captor
  private ArgumentCaptor<Collection<Message>> captor;

  @Test
  public void shouldSaveAndSend() {
    final String topic = "topic1";
    final String messageValue = "wiadomosc1";
    final Long timestamp = 123L;
    when(messagesIdGenerator.generate()).thenReturn(timestamp);

    messagesService.saveAndSend(topic, messageValue);

    verify(messagesRepository)
        .save(new Message(timestamp, topic, messageValue));
    verify(messageSender).sendMessages(captor.capture());
    assertTrue(captor.getValue()
        .contains(new Message(timestamp, topic, messageValue)));
  }

  @Test
  public void shouldSaveAndSendMultipleMessages() {
    final String topic = "topic1";
    final String messageValueA = "wiadomosc1";
    final String messageValueB = "wiadomosc2";
    final Long timestampA = 123L;
    final Long timestampB = 124L;

    final int[] index = {0};
    when(messagesIdGenerator.generate()).thenAnswer(invocationOnMock -> {
      if (index[0] == 0) {
        index[0]++;
        return timestampA;
      } else {
        return timestampB;
      }
    });

    messagesService.saveAndSend(topic, messageValueA, messageValueB);

    verify(messageSender).sendMessages(captor.capture());
    assertTrue(captor.getValue()
        .contains(new Message(timestampA, topic, messageValueA)));
    assertTrue(captor.getValue()
        .contains(new Message(timestampB, topic, messageValueB)));
  }
}