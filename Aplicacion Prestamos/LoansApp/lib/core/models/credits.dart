class Credit {
  String title;
  String amount;
  String date;

  Credit(
    this.title,
    this.amount,
    this.date,
  );
}

List<Credit> credits() {
  return [
    Credit(
      'Crédito 1',
      '\$450',
      '14 Noviembre 2023',
    ),
    Credit(
      'Crédito 2',
      '\$4500',
      '14 Noviembre 2023',
    ),
    Credit(
      'Crédito 3',
      '\$45',
      '14 Noviembre 2023',
    ),
    Credit(
      'Crédito 4',
      '\$45050',
      '10 Noviembre 2022',
    ),
    Credit(
      'Crédito 5',
      '\$1650',
      '14 Noviembre 2023',
    )
  ];
}
