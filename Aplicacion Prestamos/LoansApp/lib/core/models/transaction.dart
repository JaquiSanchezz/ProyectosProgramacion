import 'dart:math';

import 'package:flutter/material.dart';

import '../../values/app_colors.dart' as color;

class Transaction {
  String title;
  String amount;
  String date;
  IconData iconData;
  Color color;

  Transaction(this.title, this.amount, this.date, this.iconData, this.color);
}

List<Transaction> transactions() {
  return [
    Transaction('Comida', '\$450', '14 Noviembre 2023', Icons.fastfood_rounded,
        color.AppColors.orangeColor),
    Transaction('Medicina', '\$4500', '14 Noviembre 2023',
        Icons.medical_information_rounded, color.AppColors.greenColor),
    Transaction('Ropa', '\$45', '14 Noviembre 2023', Icons.shop,
        color.AppColors.accentColor),
    Transaction('Casa', '\$45050', '10 Noviembre 2022', Icons.house_rounded,
        randomColor()),
    Transaction('Alquiler de vehículos', '\$1650', '14 Noviembre 2023',
        Icons.car_rental_rounded, randomColor())
  ];
}

Color randomColor() {
  return Color(Random().nextInt(0xffffffff)).withAlpha(0xff);
}
