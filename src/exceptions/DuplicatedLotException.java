package exceptions;

public class DuplicatedLotException extends LotteryCreationException {
        private String message;
        public DuplicatedLotException(String toyName) {
            super();
            this.message = "ОШИБКА ПРИ СОЗДАНИИ ЛОТЕРЕИ: Лот \"%s\" уже есть в списке лотов. ПРОГРАММА ЗАВЕРШАЕТ РАБОТУ.";
        }
        @Override
        public String getMessage() {
            return message;
        }
    }
