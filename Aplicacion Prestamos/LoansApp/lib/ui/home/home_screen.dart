// ignore_for_file: use_build_context_synchronously, depend_on_referenced_packages

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:loans_app/ui/accounts/accounts_screen.dart';
import 'package:loans_app/ui/loans/loans_screen.dart';
import 'package:loans_app/ui/login/login_page.dart';
import 'package:loans_app/ui/transactions/transactions_screen.dart';
import 'package:loans_app/utils/database_helper.dart';
import 'package:loans_app/values/app_colors.dart' as color;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:intl/intl.dart';
import 'dart:ui' as ui;

final dbHelper = DatabaseHelper();

class TabHomeScreen extends StatefulWidget {
  const TabHomeScreen({Key? key}) : super(key: key);

  @override
  State<TabHomeScreen> createState() => _TabHomeScreenState();
}

class _TabHomeScreenState extends State<TabHomeScreen> {
  int currentIndex = 0;
  String maxLoanAmount = "";

  @override
  void initState() {
    super.initState();
    initDB();
  }

  Future<void> initDB() async {
    await dbHelper.init();

    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final String? email = prefs.getString('email');
    maxLoanAmount = prefs.getString('max_loan_amount') ?? '';

    if (email == null) {
      Navigator.pushAndRemoveUntil<void>(
        context,
        MaterialPageRoute<void>(
            builder: (BuildContext context) => const LoginPage()),
        ModalRoute.withName('/'),
      );
    }
  }

  final List<Widget> _children = [
    const HomeScreen(),
    const TransactionsScreen(),
    const LoansScreen(),
    const AccountScreen()
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: color.AppColors.backgroundColor,
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentIndex,
        selectedItemColor: color.AppColors.accentColor,
        unselectedItemColor: color.AppColors.disableColor,
        showUnselectedLabels: true,
        onTap: (newIndex) => {
          setState(() {
            currentIndex = newIndex;
          })
        },
        items: const [
          BottomNavigationBarItem(
              icon: Icon(Icons.home_filled), label: 'Inicio'),
          BottomNavigationBarItem(
              icon: Icon(Icons.pie_chart), label: 'Movimientos'),
          BottomNavigationBarItem(
              icon: Icon(Icons.bar_chart), label: 'Credito'),
          BottomNavigationBarItem(
              icon: Icon(Icons.credit_card), label: 'Cuentas'),
        ],
      ),
      body: _children[currentIndex],
    );
  }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const SafeArea(
      child: SingleChildScrollView(
        child: Column(
          children: [
            TopSection(),
            SizedBox(
              height: 20,
            ),
            ActionSection(),
            SizedBox(
              height: 20,
            ),
            TransactionSection()
          ],
        ),
      ),
    );
  }
}

class CurrentLoanInfoPayment extends StatefulWidget {
  const CurrentLoanInfoPayment({super.key});

  @override
  State<CurrentLoanInfoPayment> createState() => _CurrentLoanInfoPaymentState();
}

class _CurrentLoanInfoPaymentState extends State<CurrentLoanInfoPayment> {
  List<Map<String, dynamic>> items = [];
  int userId = 0;

  @override
  void initState() {
    super.initState();
    getLastLoan();
  }

  Future<void> getLastLoan() async {
    await dbHelper.init();
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final id = prefs.getInt('userId');
    final result = await dbHelper.getLoansByStatus('active', id ?? 0);

    if (result.isNotEmpty) {
      final res = await dbHelper.getPendingPayments(result[0]["_id"]);

      setState(() {
        items = res;
        userId = id ?? 0;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    var numberFormat = NumberFormat.currency(locale: 'es_MX', symbol: "\$");
    var amount = '';
    var limitDate = "";
    var paymentId = 0;

    if (items.isNotEmpty) {
      amount = numberFormat.format(double.parse(items[0]['amount']));
      limitDate = items[0]['limit_date'];
      paymentId = items[0]['_id'];
    }

    return Stack(
      children: [
        Visibility(
            visible: items.isNotEmpty,
            child: Container(
                margin: const EdgeInsets.symmetric(horizontal: 30),
                padding: const EdgeInsets.all(0),
                decoration: BoxDecoration(
                    color: Colors.amber,
                    borderRadius: BorderRadius.circular(20)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(20)),
                      child: Padding(
                        padding: const EdgeInsets.all(12),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'A pagar',
                                  style: TextStyle(
                                      fontSize: 14,
                                      fontWeight: FontWeight.normal,
                                      color: color.AppColors.disableColor),
                                ),
                                Text(
                                  amount,
                                  style: const TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.bold,
                                      color: Colors.black),
                                )
                              ],
                            ),
                            SizedBox(
                              height: 35,
                              width: 110,
                              child: Directionality(
                                  textDirection: ui.TextDirection.rtl,
                                  child: FilledButton.icon(
                                      onPressed: () {
                                        addPayment(context, paymentId, amount);
                                      },
                                      icon: const Icon(
                                        CupertinoIcons.chevron_right,
                                        color: Colors.white,
                                        textDirection: ui.TextDirection.ltr,
                                        size: 18,
                                      ),
                                      style: const ButtonStyle().copyWith(
                                        backgroundColor:
                                            MaterialStateProperty.all(
                                                color.AppColors.accentColor),
                                        iconColor: MaterialStateProperty.all(
                                            Colors.white),
                                        shape: MaterialStateProperty.all(
                                          RoundedRectangleBorder(
                                            borderRadius:
                                                BorderRadius.circular(20),
                                          ),
                                        ),
                                      ),
                                      label: const Text(
                                        "Pagar",
                                        style: TextStyle(
                                            color: Colors.white, fontSize: 14),
                                      ))),
                            )
                          ],
                        ),
                      ),
                    ),
                    Center(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          const Text(
                            'Fecha límite de pago:',
                            style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.bold,
                                color: Colors.black),
                          ),
                          const SizedBox(
                            width: 5,
                          ),
                          Container(
                              padding: const EdgeInsets.only(top: 4, bottom: 4),
                              child: Text(
                                limitDate,
                                style: const TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.normal,
                                    color: Colors.black),
                              ))
                        ],
                      ),
                    )
                  ],
                ))),
      ],
    );
  }

  addPayment(BuildContext context, int paymentId, String amount) async {
    var today = DateTime.now();
    var dateFormatTime = DateFormat('dd-MM-yyyy hh:mm');
    final infoUpdate = {
      'payment_date': dateFormatTime.format(today),
      '_id': paymentId
    };

    final transactionRequestAccepted = {
      'user_id': userId,
      'name': 'Pago de $amount realizado.',
      'date': dateFormatTime.format(DateTime.now())
    };

    try {
      final res = await dbHelper.update('payments', infoUpdate);
      await dbHelper.insertInfo('transactions', transactionRequestAccepted);
      if (res > 0) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('¡Pago agregado correctamente!'),
          ),
        );
        setState(() {});
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('¡Ocurrio un error al procesar el pago!'),
          ),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('¡Ocurrio un error al procesar el pago!'),
        ),
      );
    }
  }
}

class TransactionSection extends StatelessWidget {
  const TransactionSection({Key? key}) : super(key: key);

  Future<List<Map<String, dynamic>>> getTransactions() async {
    await dbHelper.init();
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final id = prefs.getInt('userId');
    final result = await dbHelper.getInfoByUser('transactions', id ?? 0);

    return result;
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 30),
      child: FutureBuilder(
          future: getTransactions(),
          builder: (c, s) {
            if (s.hasData) {
              final items = s.data;
              return Visibility(
                  visible: items!.isNotEmpty,
                  child: Column(
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          const Text(
                            'Movimientos',
                            style: TextStyle(
                                fontSize: 22,
                                fontWeight: FontWeight.bold,
                                color: Colors.black),
                          ),
                          Text(
                            'Ver todos',
                            style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.normal,
                                color: color.AppColors.accentColor),
                          )
                        ],
                      ),
                      const SizedBox(
                        height: 10,
                      ),
                      Container(
                        height: 300,
                        margin: const EdgeInsets.only(bottom: 20),
                        padding: const EdgeInsets.symmetric(horizontal: 20),
                        decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.circular(20)),
                        child: ListView.builder(
                            padding: const EdgeInsets.symmetric(vertical: 10),
                            itemCount: items.length,
                            itemBuilder: (context, index) {
                              return Padding(
                                padding:
                                    const EdgeInsets.only(top: 10, bottom: 10),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Expanded(
                                      child: Column(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.start,
                                        children: [
                                          Text(
                                            items[index]['name'],
                                            style: const TextStyle(
                                                fontSize: 16,
                                                fontWeight: FontWeight.bold,
                                                color: Colors.black),
                                          ),
                                          Text(
                                            items[index]['date'],
                                            style: TextStyle(
                                                fontSize: 14,
                                                fontWeight: FontWeight.normal,
                                                color: color
                                                    .AppColors.disableColor),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ],
                                ),
                              );
                            }),
                      )
                    ],
                  ));
            } else {
              return const Center(child: CircularProgressIndicator());
            }
          }),
    );
  }
}

class ActionSection extends StatelessWidget {
  const ActionSection({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20),
      margin: const EdgeInsets.symmetric(horizontal: 30),
      decoration: BoxDecoration(
          color: Colors.white, borderRadius: BorderRadius.circular(20)),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          GestureDetector(
            onTap: () => {},
            child: ActionItem(
                icon: Icons.payments_outlined,
                color: color.AppColors.accentColor,
                title: 'Solicitar'),
          ),
          ActionItem(
            icon: Icons.credit_card_outlined,
            color: color.AppColors.orangeColor,
            title: 'Transferir',
          ),
          ActionItem(
            icon: Icons.dashboard_outlined,
            color: color.AppColors.disableColor,
            title: 'Más',
          )
        ],
      ),
    );
  }
}

class ActionItem extends StatelessWidget {
  const ActionItem(
      {Key? key, required this.icon, required this.color, required this.title})
      : super(key: key);
  final IconData icon;
  final Color color;
  final String title;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
          decoration: BoxDecoration(
              color: color.withOpacity(0.2),
              borderRadius: BorderRadius.circular(30)),
          child: Icon(
            icon,
            color: color,
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        Text(
          title,
          style: const TextStyle(
              fontSize: 14, fontWeight: FontWeight.w600, color: Colors.black),
        )
      ],
    );
  }
}

class TopSection extends StatefulWidget {
  const TopSection({super.key});

  @override
  State<TopSection> createState() => _TopSectionState();
}

class _TopSectionState extends State<TopSection> {
  bool status = true;
  String amount = "";
  String total = "";
  String current = "";
  double percentAvailable = 0;

  @override
  void initState() {
    super.initState();
    checkStatus();
  }

  checkStatus() async {
    await dbHelper.init();
    var numberFormat = NumberFormat.currency(locale: 'es_MX', symbol: "\$");

    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final info = prefs.getString('max_loan_amount') ?? '0';
    final id = prefs.getInt('userId');
    double aux = 0.0;

    final loans = await dbHelper.getLoansByStatus("active", id ?? 0);
    print("loans");
    print(loans);

    for (var loan in loans) {
      aux += double.parse(loan['amount'].toString());
    }

    final currentMaxAmount = double.parse(info) - aux;

    final percentAux = (aux / double.parse(info)) * 100;

    final percent = ((percentAux - 0) / (100 - 0)).clamp(0, 1).toDouble();

    if (loans.isNotEmpty) {
      final res = await dbHelper.getPendingPayments(loans[0]["_id"]);

      if (res.isEmpty) {
        setState(() {
          percentAvailable = percent;
          status = false;
          amount = numberFormat.format(double.parse(info));
          total = numberFormat.format(aux);

          current = numberFormat.format(currentMaxAmount);
        });
      } else {
        setState(() {
          percentAvailable = percent;
          status = true;
          amount = numberFormat.format(double.parse(info));
          total = numberFormat.format(aux);

          current = numberFormat.format(currentMaxAmount);
        });
      }
    } else {
      setState(() {
        percentAvailable = percent;
        status = false;
        amount = numberFormat.format(double.parse(info));
        total = numberFormat.format(aux);

        current = numberFormat.format(currentMaxAmount);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          color: color.AppColors.backgroundColor,
          padding: const EdgeInsets.all(10),
          alignment: Alignment.topCenter,
          height: 250,
        ),
        Container(
          padding: const EdgeInsets.all(10),
          alignment: Alignment.topCenter,
          height: status ? 380 : 280,
          decoration: BoxDecoration(
              color: color.AppColors.accentColor,
              borderRadius: const BorderRadius.only(
                  bottomLeft: Radius.circular(40),
                  bottomRight: Radius.circular(40))),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Expanded(
                  child: GestureDetector(
                child: Image.asset(
                  'assets/images/man.png',
                  width: 40,
                  height: 40,
                  alignment: Alignment.topLeft,
                ),
                onTap: () async {
                  SharedPreferences prefs =
                      await SharedPreferences.getInstance();
                  await prefs.remove('email');

                  Navigator.pushAndRemoveUntil<void>(
                    context,
                    MaterialPageRoute<void>(
                        builder: (BuildContext context) => const LoginPage()),
                    ModalRoute.withName('/'),
                  );
                },
              )),
              Image.asset(
                'assets/images/search.png',
                width: 25,
                height: 25,
                color: Colors.white,
              ),
              const SizedBox(
                width: 20,
              ),
              Image.asset(
                'assets/images/bell.png',
                width: 25,
                height: 25,
                color: Colors.white,
              )
            ],
          ),
        ),
        Positioned(
          top: 80,
          left: 0,
          right: 0,
          child: Container(
              margin: const EdgeInsets.symmetric(horizontal: 30),
              padding: const EdgeInsets.all(15),
              decoration: BoxDecoration(
                  color: Colors.white, borderRadius: BorderRadius.circular(20)),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Crédito disponible',
                            style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.normal,
                                color: color.AppColors.disableColor),
                          ),
                          Text(
                            amount,
                            style: const TextStyle(
                                fontSize: 30,
                                fontWeight: FontWeight.bold,
                                color: Colors.black),
                          )
                        ],
                      ),
                      CircleAvatar(
                        radius: 20,
                        child: Image.asset(
                          'assets/images/mx.png',
                        ),
                      )
                    ],
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  Padding(
                    padding: const EdgeInsets.only(bottom: 10),
                    child: SizedBox(
                      height: 5,
                      child: LinearProgressIndicator(
                        borderRadius:
                            const BorderRadius.all(Radius.circular(20)),
                        value: percentAvailable,
                        semanticsLabel: "",
                      ),
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Row(
                        children: [
                          Text(
                            'Gastado',
                            style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.normal,
                                color: color.AppColors.disableColor),
                          ),
                          const SizedBox(
                            width: 5,
                          ),
                          Container(
                              padding: const EdgeInsets.only(top: 4, bottom: 4),
                              child: Text(
                                total,
                                style: const TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.black),
                              ))
                        ],
                      ),
                      Row(
                        children: [
                          Text(
                            'MX Pesos',
                            style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.normal,
                                color: color.AppColors.accentColor),
                          ),
                          Icon(
                            Icons.arrow_drop_down,
                            color: color.AppColors.accentColor,
                          )
                        ],
                      )
                    ],
                  )
                ],
              )),
        ),
        const Positioned(
            top: 260, left: 0, right: 0, child: CurrentLoanInfoPayment())
      ],
    );
  }
}
