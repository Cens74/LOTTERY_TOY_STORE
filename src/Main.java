import model.Toy;

public class Main {
    public static void main(String[] args) {
        Toy toy1 = new Toy(1, "Кукла резиновая", 20);
        Toy toy2 = new Toy(2, "Медведь плюшевый коричневый", 20);
        Toy toy3 = new Toy(3, "Конструктор \"Маленький строитель\"", 30);
        Toy toy4 = new Toy(4, "Пластилин \'Радуга 12 цв.\"", 16);
        Toy toy5 = new Toy(5, "Чебурашка пластм.", 14);
        Toy toy6 = new Toy(1, "Кукла резиновая", 20);
        System.out.println(toy1.toString() + ", HASH_CODE = " + toy1.hashCode());
        System.out.println(toy2.toString() + ", HASH_CODE = " + toy2.hashCode());
        System.out.println(toy3.toString() + ", HASH_CODE = " + toy3.hashCode());
        System.out.println(toy4.toString() + ", HASH_CODE = " + toy4.hashCode());
        System.out.println(toy5.toString() + ", HASH_CODE = " + toy5.hashCode());
        System.out.println(toy6.toString() + ", HASH_CODE = " + toy6.hashCode());
        System.out.println(toy1.equals(toy6));
    }
}