//Sirven para el manejo de la informacion que se presentara en la interfaz

class Account {
  String alias;
  String fullname;
  String bank;
  String account;

  Account(this.alias, this.fullname, this.bank, this.account);
}

List<Account> accounts() {
  return [
    Account('BRENDA M **019', 'BRENDA MORALES PEREZ', 'BBVA', ""),
    Account('DULCE G **397', 'DULCE GALVAN CORDOVA', 'BANAMEX', ""),
    Account('JOSE C **002', 'JOSE ANGEL CAMARGO', 'SANTANDER', ""),
  ];
}
