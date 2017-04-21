package vt.smt.Commands;

import java.io.Serializable;
/**
  *  Api, предоставляемый сервером клиенту в виде jar-библиотеки
 *   предполагается, что клиент будет отправлять серверу команды в виде объектов,
 *   в конструктор которых нужно передать необходимые данные.
 *
 */
public interface ServerCommand extends Serializable {

}
