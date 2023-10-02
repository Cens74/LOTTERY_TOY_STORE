package exceptions;

import utils.Tuner;

public class DuplicatedLotException extends LotteryCreationException {
        private String message;
        public DuplicatedLotException(String toyName) {
            super();
            this.message = Tuner.ANSI_RED+"ОШИБКА ПРИ СОЗДАНИИ ЛОТЕРЕИ: Лот \"%s\" уже есть в списке лотов. ПРОГРАММА ЗАВЕРШАЕТ РАБОТУ."+Tuner.ANSI_RESET;
        }
        @Override
        public String getMessage() {
            return message;
        }
    }
