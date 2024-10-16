package com.atm;

import java.util.*;

public class ATM {
    private static Map<Integer, Customer> customerDetails = new HashMap<>();
    private static List<Transaction> transactions = new ArrayList<>();
    
    public static void main(String[] args) {
        initializeData();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("Enter Choice:\n1.Customer Login\n2.Exit ");
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    customerLogin(sc);
                    break;
                case 2:
                    System.out.println("Thanks for Using!!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter Valid Choice");
            }
        }
    }

    private static void initializeData() {
    
        Customer customer = new Customer(12345, "9876", "Pavan", 10000);
        customerDetails.put(12345, customer);
        

        Customer anotherCustomer = new Customer(67890, "1234", "Shiva", 5000);
        customerDetails.put(67890, anotherCustomer);
    }

    private static void customerLogin(Scanner sc) {
        System.out.println("Enter Account Number:");
        int cardNumber = sc.nextInt();
        System.out.println("Enter PIN :");
        String pin = sc.next();

        Customer customer = customerDetails.get(cardNumber);
        if (customer != null && customer.getPin().equals(pin)) {
            System.out.println("Hello " + customer.getName() + " Welcome to our Bank");
            handleTransactions(sc, customer);
        } else {
            System.out.println("Invalid card number or pin.");
        }
    }

    private static void handleTransactions(Scanner sc, Customer customer) {
        while (true) {
            System.out.println("Enter Choice:\n1.Check The Balance\n2.Withdraw Money\n3.Deposit Money\n4.Transfer\n5.Mini Statement\n6.Exit ");
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    checkBalance(customer);
                    break;
                case 2:
                    withdrawMoney(sc, customer);
                    break;
                case 3:
                    depositMoney(sc, customer);
                    break;
                case 4:
                    transferMoney(sc, customer);
                    break;
                case 5:
                    printStatement(customer);
                    break;
                case 6:
                    System.out.println("Thanks for Using!!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter correct Choice in User menu");
            }
        }
    }

    private static void checkBalance(Customer customer) {
        System.out.println("Current Balance is: " + customer.getBalance());
    }

    private static void withdrawMoney(Scanner sc, Customer customer) {
        System.out.println("Enter Amount to Withdraw: ");
        int amount = sc.nextInt();
        
        if (amount <= customer.getBalance()) {
            customer.setBalance(customer.getBalance() - amount);
            transactions.add(new Transaction(customer.getCardNumber(), "Withdraw", amount));
            System.out.println("Withdrawal successful. Your Updated Balance: " + customer.getBalance());
        } else {
            System.out.println("Insufficient Balance");
        }
    }

    private static void depositMoney(Scanner sc, Customer customer) {
        System.out.println("Enter Amount to Deposit: ");
        int amount = sc.nextInt();
        
        customer.setBalance(customer.getBalance() + amount);
        transactions.add(new Transaction(customer.getCardNumber(), "Deposit", amount));
        System.out.println("Deposit successful. New Balance: " + customer.getBalance());
    }

    private static void transferMoney(Scanner sc, Customer customer) {
        System.out.println("Enter Account Number for Transfer: ");
        int recipientAccount = sc.nextInt();
        System.out.println("Enter Amount to Transfer: ");
        int amount = sc.nextInt();
        
        if (amount <= customer.getBalance()) {
            Customer recipient = customerDetails.get(recipientAccount);
            if (recipient != null) {
                // Perform the transfer
                customer.setBalance(customer.getBalance() - amount);
                recipient.setBalance(recipient.getBalance() + amount);
                transactions.add(new Transaction(customer.getCardNumber(), "Transfer to " + recipientAccount, amount));
                transactions.add(new Transaction(recipient.getCardNumber(), "Transfer from " + customer.getCardNumber(), amount));
                System.out.println("Transfer successful. Your Updated Balance: " + customer.getBalance());
            } else {
                System.out.println("Recipient account does not exist.");
            }
        } else {
            System.out.println("Insufficient Balance.");
        }
    }

    private static void printStatement(Customer customer) {
        System.out.println("Mini Statement for Account Number: " + customer.getCardNumber());
        for (Transaction t : transactions) {
            if (t.getCardNumber() == customer.getCardNumber()) {
                System.out.println(t);
            }
        }
    }
}

class Customer {
    private String pin;
    private String name;
    private int balance;
    private int cardNumber;

    public Customer(int cardNumber, String pin, String name, int balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}

class Transaction {
    private int cardNumber;
    private String type;
    private int amount;

    public Transaction(int cardNumber, String type, int amount) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.amount = amount;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return cardNumber + " " + type + " " + amount;
    }
}