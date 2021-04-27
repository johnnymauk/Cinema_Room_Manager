package cinema;

import java.util.Scanner;

public class Cinema {
    final public Scanner sc;
    final int rows;
    final int seats;
    final String[][] theatre;
    int[] prices = {10, 8};
    boolean isLarge;
    int midway;
    int purchased;
    int income;

    public static void main(String[] args) {
        Cinema c = new Cinema();
        c.initSeats();
        int menu = -1;

        while (menu != 0){
            System.out.println();
            System.out.println("Select an option:");
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            menu = c.sc.nextInt();
            System.out.println();
            switch (menu) {
                case 0:
                    System.out.println("Thank you, goodbye!");
                    break;
                case 1:
                    c.printSeats();
                    break;
                case 2:
                    c.buyTicket();
                    break;
                case 3:
                    c.getStats();
                    break;
                default:
                    do {
                        System.out.println("Error, please enter a valid option.");
                        menu = c.sc.nextInt();
                    } while (menu < 0 || menu > 2);
                    break;
            }
        }

    }

    public Cinema () {
        this.sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        this.rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        this.seats = sc.nextInt();
        this.isLarge = this.rows * this.seats > 60;
        this.midway = this.rows % 2 == 0 ? this.rows / 2 : (this.rows - 1) / 2;
        this.theatre = new String[this.rows + 1][this.seats + 1];
        this.income = 0;
        this.purchased = 0;
    }

    public void getStats () {
        System.out.printf("Number of purchased tickets: %d\n", this.purchased);
        System.out.printf("Percentage: %.2f%%\n", (float) this.purchased * 100 / this.rows / this.seats );
        System.out.printf("Current income: $%d\n", this.income);
        this.calcIncome();
    }

    public void initSeats () {
        this.theatre[0][0] = " ";
        for (int ii = 1; ii <= this.rows; ii++) {
            this.theatre[ii][0] = String.valueOf(ii);
        }

        for (int jj = 1; jj <= this.seats; jj++) {
            this.theatre[0][jj] = String.valueOf(jj);
        }

        for (int row = 1; row <= this.rows; row++) {
            for (int seat = 1; seat <= this.seats; seat++) {
                this.theatre[row][seat] = "S";
            }
        }
    }

    public void buyTicket () {
        int brow = 0;
        int bseat = 0;
        while (!this.theatre[brow][bseat].equals("S")) {
            System.out.println("Enter a row number:");
            brow = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            bseat = sc.nextInt();
            System.out.println();
            if (brow < 1 || brow > this.rows || bseat < 1 || bseat > this.seats) {
                System.out.println("Wrong input!\n");
                brow = 0;
                bseat = 0;
            }
            if (this.theatre[brow][bseat].equals("B")) {
                System.out.println("That ticket has already been purchased!\n");
            }
        }
        this.theatre[brow][bseat] = "B";
        int tp = !this.isLarge || brow <= this.midway ? this.prices[0] : this.prices[1];
        System.out.printf("Ticket price: $%d\n", tp);
        this.income += tp;
        this.purchased++;
    }

    public void printSeats () {
        System.out.println("Cinema:");
        for (int row = 0; row <= this.rows; row++) {
            for (int seat = 0; seat <= this.seats; seat++) {
                System.out.printf("%s ", this.theatre[row][seat]);
            }
            System.out.print("\n");
        }
    }

    public void calcIncome () {
        System.out.print("Total income: ");
        if (this.rows * this.seats <= 60){
            System.out.printf("$%d\n", this.rows * this.seats * this.prices[0]);
        } else {
            int[] halves = new int[2];
            halves[0] = this.rows % 2 == 0 ? this.rows / 2 : (this.rows - 1) / 2;
            halves[1] = this.rows % 2 == 0 ? this.rows / 2 : (this.rows + 1) / 2;
            System.out.printf("$%d\n", this.seats * (halves[0] * this.prices[0] + halves[1] * this.prices[1]));
        }
    }

}