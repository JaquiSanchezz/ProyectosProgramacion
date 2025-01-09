// ignore_for_file: depend_on_referenced_packages, use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:loans_app/components/app_text_form_field_amount_loan.dart';
import 'package:loans_app/utils/database_helper.dart';
import 'package:loans_app/values/app_colors.dart' as color;
import 'package:intl/intl.dart';
import 'dart:ui' as ui;

import 'package:shared_preferences/shared_preferences.dart';

final dbHelper = DatabaseHelper();

class RequestLoanScreen extends StatefulWidget {
  const RequestLoanScreen({Key? key}) : super(key: key);

  @override
  State<RequestLoanScreen> createState() => RequestLoanState();
}

class RequestLoanState extends State<RequestLoanScreen> {
  final _formKey = GlobalKey<FormState>();
  double globalMaxAmount = 0;
  String globalAmount = "";
  TextEditingController amountController = TextEditingController();

  List<String> list = <String>[
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '10'
  ];
  String numPaymentsDropdownValue = '1';

  List<String> paymentPlan = <String>[];
  String paymentPlanDropdownValue = '';

  List<Map<String, dynamic>> accountsInfo = [];
  List<String> accounts = <String>[];
  String accountDropdownValue = '';

  List<String> reasons = <String>[
    'Necesidades diarias',
    'Transporte',
    'Comida',
    'Entretenimiento'
  ];
  String reasonsDropdownValue = 'Necesidades diarias';
  bool checkedContract = false;
  int com = 22;
  int comAux = 22;
  int selectedButton = 0;
  String paymentFirstimitDate = '';
  String totalCharges = "";
  double totalChargesValue = 0;

  @override
  void initState() {
    super.initState();
    initDB();
  }

  Future<void> initDB() async {
    await dbHelper.init();
    var numberFormat = NumberFormat.currency(locale: 'es_MX', symbol: "\$");
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final info = prefs.getString('max_loan_amount') ?? '0';
    double aux = 0.0;

    final id = prefs.getInt('userId');

    final loans = await dbHelper.getLoansByStatus("active", id ?? 0);

    for (var loan in loans) {
      aux += double.parse(loan['amount'].toString());
    }

    final totalAmount = (double.parse(info) - aux);

    var calcTotalCharges = (com * totalAmount) / 100;

    amountController.text = totalAmount.toString();

    final accountsDB = await dbHelper.getInfoByUser('accounts', id ?? 0);
    List<String> accountsAux = <String>[];
    List<String> paymentPlanAux = <String>[];
    for (var account in accountsDB) {
      accountsInfo.add(account);
      accountsAux.add('${account["bank"]} / (${account["number"]})');
    }

    var today = DateTime.now();
    var dateFormat = DateFormat('dd-MM-yyyy');
    var dateFormatPaymentPlan = DateFormat('MMMd');
    String dateStr = dateFormat.format(today.add(const Duration(days: 15)));

    final paymentByPeriod =
        (totalAmount + calcTotalCharges) / int.parse(numPaymentsDropdownValue);

    final valuePaymentPlan =
        "${dateFormatPaymentPlan.format(today.add(const Duration(days: 15)))}. ${numberFormat.format(paymentByPeriod)} MXN";

    paymentPlanAux.add(valuePaymentPlan);

    setState(() {
      globalMaxAmount = totalAmount;
      globalAmount = totalAmount.toString();
      paymentFirstimitDate = dateStr;
      accounts = accountsAux;
      totalChargesValue = calcTotalCharges;
      totalCharges = numberFormat.format(calcTotalCharges);
      paymentPlan = paymentPlanAux;
      paymentPlanDropdownValue = valuePaymentPlan;

      if (accounts.isNotEmpty) {
        accountDropdownValue = accounts[0];
      }
    });
  }

  recalculate() {
    final totalAmount = double.parse(globalAmount);
    var numberFormat = NumberFormat.currency(locale: 'es_MX', symbol: "\$");
    List<String> paymentPlanAux = <String>[];
    var daysToAdd = 15;

    var comAux = com;
    if (selectedButton == 0) {
      daysToAdd = 15;
      comAux = com;
    } else if (selectedButton == 1) {
      daysToAdd = 30;
      comAux = 28;
    } else {
      daysToAdd = 61;
      comAux = 33;
    }

    var calcTotalCharges = (comAux * totalAmount) / 100;

    var today = DateTime.now();
    var dateFormatPaymentPlan = DateFormat('MMMd');

    final paymentByPeriod =
        (totalAmount + calcTotalCharges) / int.parse(numPaymentsDropdownValue);

    final dateFormatedCompleted =
        dateFormatPaymentPlan.format(today.add(Duration(days: daysToAdd)));

    final valuePaymentPlan =
        "$dateFormatedCompleted. ${numberFormat.format(paymentByPeriod)} MXN";

    paymentPlanAux.add(valuePaymentPlan);
    setState(() {
      comAux = comAux;
      totalChargesValue = calcTotalCharges;
      totalCharges = numberFormat.format(calcTotalCharges);
      paymentPlan = paymentPlanAux;
      paymentPlanDropdownValue = valuePaymentPlan;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: color.AppColors.backgroundColor,
      appBar: AppBar(
        leading: IconButton(
          icon: const Icon(
            Icons.arrow_back_ios,
            color: Colors.white,
          ),
          color: Colors.white,
          onPressed: () => {Navigator.pop(context)},
        ),
        title: const Text(
          'Solicitar prestamo',
          style: TextStyle(color: Colors.white, fontSize: 20),
        ),
        forceMaterialTransparency: false,
        backgroundColor: color.AppColors.accentColor,
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Form(
              key: _formKey,
              child: Padding(
                padding: const EdgeInsets.all(20),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      "¿Cuánto te gustaría recibir?",
                      style:
                          TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    const Text(
                      "Recuerda que los depósitos pueden retrasarse durante los fines de semana y días festivos.",
                      style: TextStyle(
                          fontSize: 14, color: color.AppColors.contractText),
                    ),
                    Container(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12)),
                      margin: const EdgeInsets.only(top: 20),
                      child: Padding(
                        padding: const EdgeInsets.all(14),
                        child: Column(
                          children: [
                            const Text(
                              "El monto de tu crédito (MXN\$)",
                              style: TextStyle(
                                  fontSize: 14,
                                  color: color.AppColors.secondary),
                            ),
                            const SizedBox(
                              height: 20,
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                IconButton(
                                    style: const ButtonStyle().copyWith(
                                      shape: MaterialStateProperty.all(
                                        RoundedRectangleBorder(
                                          borderRadius:
                                              BorderRadius.circular(20),
                                        ),
                                      ),
                                      backgroundColor:
                                          MaterialStateProperty.all(
                                              color.AppColors.primaryColor),
                                    ),
                                    onPressed: () {
                                      try {
                                        final current =
                                            double.parse(globalAmount) - 100;

                                        if (current > 0) {
                                          amountController.text =
                                              current.toString();
                                          setState(() {
                                            globalAmount = current.toString();
                                          });
                                          recalculate();
                                        } else {
                                          ScaffoldMessenger.of(context)
                                              .showSnackBar(
                                            const SnackBar(
                                              content: Text(
                                                  '¡La cantidad minima que puedes solicitar es: \$100 MXN!'),
                                            ),
                                          );
                                        }
                                      } catch (e) {
                                        ScaffoldMessenger.of(context)
                                            .showSnackBar(
                                          const SnackBar(
                                            content:
                                                Text('¡Datos incorrectos!'),
                                          ),
                                        );
                                      }
                                    },
                                    icon: const Icon(
                                      Icons.remove,
                                      color: Colors.white,
                                    )),
                                const SizedBox(
                                  width: 10,
                                ),
                                SizedBox(
                                  width: 130,
                                  child: AppTextFormFieldAmountLoan(
                                    autofocus: true,
                                    labelText: '',
                                    hintText: "",
                                    keyboardType: TextInputType.number,
                                    textInputAction: TextInputAction.next,
                                    onChanged: (value) {
                                      try {
                                        final current = double.parse(value);

                                        if (current <= globalMaxAmount) {
                                          amountController.text = value;
                                          setState(() {
                                            globalAmount = current.toString();
                                          });
                                          recalculate();
                                        } else {
                                          var numberFormat =
                                              NumberFormat.currency(
                                                  locale: 'es_MX',
                                                  symbol: "\$");
                                          final max = numberFormat
                                              .format(globalMaxAmount);
                                          ScaffoldMessenger.of(context)
                                              .showSnackBar(
                                            SnackBar(
                                              content: Text(
                                                  '¡La cantidad maxima que puedes solicitar es: $max MXN!'),
                                            ),
                                          );
                                        }
                                      } catch (e) {
                                        ScaffoldMessenger.of(context)
                                            .showSnackBar(
                                          const SnackBar(
                                            content:
                                                Text('¡Datos incorrectos!'),
                                          ),
                                        );
                                      }

                                      _formKey.currentState?.validate();
                                    },
                                    validator: (value) {
                                      return value!.isEmpty
                                          ? 'Por favor, introduzca una cantidad.'
                                          : value.toString().length >= 3
                                              ? null
                                              : 'Número inválido';
                                    },
                                    controller: amountController,
                                  ),
                                ),
                                const SizedBox(
                                  width: 10,
                                ),
                                IconButton(
                                    style: const ButtonStyle().copyWith(
                                      shape: MaterialStateProperty.all(
                                        RoundedRectangleBorder(
                                          borderRadius:
                                              BorderRadius.circular(20),
                                        ),
                                      ),
                                      backgroundColor:
                                          MaterialStateProperty.all(
                                              color.AppColors.primaryColor),
                                    ),
                                    onPressed: () {
                                      try {
                                        final current =
                                            double.parse(globalAmount) + 100;

                                        if (current <= globalMaxAmount) {
                                          amountController.text =
                                              current.toString();
                                          setState(() {
                                            globalAmount = current.toString();
                                          });
                                          recalculate();
                                        } else {
                                          var numberFormat =
                                              NumberFormat.currency(
                                                  locale: 'es_MX',
                                                  symbol: "\$");
                                          final max = numberFormat
                                              .format(globalMaxAmount);
                                          ScaffoldMessenger.of(context)
                                              .showSnackBar(
                                            SnackBar(
                                              content: Text(
                                                  '¡La cantidad maxima que puedes solicitar es: $max MXN!'),
                                            ),
                                          );
                                        }
                                      } catch (e) {
                                        ScaffoldMessenger.of(context)
                                            .showSnackBar(
                                          const SnackBar(
                                            content:
                                                Text('¡Datos incorrectos!'),
                                          ),
                                        );
                                      }
                                    },
                                    icon: const Icon(
                                      Icons.add,
                                      color: Colors.white,
                                    )),
                              ],
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            const Text(
                              "Los montos deben ser múltiplos de 100.",
                              style: TextStyle(
                                  fontSize: 14,
                                  color: color.AppColors.secondary),
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    Container(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(12),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              "Periodo del pago",
                              style:
                                  TextStyle(color: Colors.black, fontSize: 14),
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            Row(
                              children: [
                                SizedBox(
                                  width: 100,
                                  height: 30,
                                  child: FilledButton(
                                      onPressed: () {
                                        final today = DateTime.now();
                                        var dateFormat =
                                            DateFormat('dd-MM-yyyy');

                                        String dateStr = dateFormat.format(today
                                            .add(const Duration(days: 15)));

                                        setState(
                                          () {
                                            selectedButton = 0;
                                            paymentFirstimitDate = dateStr;
                                          },
                                        );
                                        recalculate();
                                      },
                                      style: const ButtonStyle().copyWith(
                                        backgroundColor: selectedButton == 0
                                            ? MaterialStateProperty.all(
                                                color.AppColors.primaryColor)
                                            : MaterialStateProperty.all(
                                                color.AppColors.grey),
                                        shape: MaterialStateProperty.all(
                                          RoundedRectangleBorder(
                                            borderRadius:
                                                BorderRadius.circular(6),
                                          ),
                                        ),
                                      ),
                                      child: const Text(
                                        "Quincenal",
                                        style: TextStyle(
                                            fontSize: 11, color: Colors.white),
                                      )),
                                ),
                                const SizedBox(
                                  width: 10,
                                ),
                                SizedBox(
                                  width: 100,
                                  height: 30,
                                  child: FilledButton(
                                      onPressed: () {
                                        final today = DateTime.now();
                                        var dateFormat =
                                            DateFormat('dd-MM-yyyy');

                                        String dateStr = dateFormat.format(today
                                            .add(const Duration(days: 30)));
                                        setState(
                                          () {
                                            selectedButton = 1;
                                            paymentFirstimitDate = dateStr;
                                          },
                                        );
                                        recalculate();
                                      },
                                      style: const ButtonStyle().copyWith(
                                        backgroundColor: selectedButton == 1
                                            ? MaterialStateProperty.all(
                                                color.AppColors.primaryColor)
                                            : MaterialStateProperty.all(
                                                color.AppColors.grey),
                                        shape: MaterialStateProperty.all(
                                          RoundedRectangleBorder(
                                            borderRadius:
                                                BorderRadius.circular(6),
                                          ),
                                        ),
                                      ),
                                      child: const Text(
                                        "Mensual",
                                        style: TextStyle(
                                            fontSize: 11, color: Colors.white),
                                      )),
                                ),
                                const SizedBox(
                                  width: 10,
                                ),
                                SizedBox(
                                  width: 100,
                                  height: 30,
                                  child: FilledButton(
                                      onPressed: () {
                                        final today = DateTime.now();
                                        var dateFormat =
                                            DateFormat('dd-MM-yyyy');

                                        String dateStr = dateFormat.format(today
                                            .add(const Duration(days: 61)));
                                        setState(
                                          () {
                                            selectedButton = 2;
                                            paymentFirstimitDate = dateStr;
                                          },
                                        );
                                        recalculate();
                                      },
                                      style: const ButtonStyle().copyWith(
                                        backgroundColor: selectedButton == 2
                                            ? MaterialStateProperty.all(
                                                color.AppColors.primaryColor)
                                            : MaterialStateProperty.all(
                                                color.AppColors.grey),
                                        shape: MaterialStateProperty.all(
                                          RoundedRectangleBorder(
                                            borderRadius:
                                                BorderRadius.circular(6),
                                          ),
                                        ),
                                      ),
                                      child: const Text(
                                        "Bimestral",
                                        style: TextStyle(
                                            fontSize: 11, color: Colors.white),
                                      )),
                                ),
                              ],
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            Row(
                              mainAxisSize: MainAxisSize.max,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                const SizedBox(
                                  width: 200,
                                  child: Text(
                                    "Número de pagos",
                                    style: TextStyle(
                                        fontSize: 13, color: Colors.black),
                                  ),
                                ),
                                const Spacer(),
                                SizedBox(
                                    width: 50,
                                    child: DropdownButton<String>(
                                      items: list.map((String value) {
                                        return DropdownMenuItem<String>(
                                          value: value,
                                          child: Text(
                                            value,
                                            textDirection: ui.TextDirection.ltr,
                                            textAlign: TextAlign.center,
                                          ),
                                        );
                                      }).toList(),
                                      onChanged: (value) {
                                        setState(() {
                                          numPaymentsDropdownValue =
                                              value.toString();
                                        });
                                        recalculate();
                                      },
                                      value: numPaymentsDropdownValue,
                                      isExpanded: true,
                                    ))
                              ],
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            Row(
                              mainAxisSize: MainAxisSize.max,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                const SizedBox(
                                  width: 200,
                                  child: Text(
                                    "Fecha límite de pago",
                                    style: TextStyle(
                                        fontSize: 13, color: Colors.black),
                                  ),
                                ),
                                const Spacer(),
                                SizedBox(
                                    width: 80,
                                    child: Text(
                                      paymentFirstimitDate,
                                      style: const TextStyle(
                                          fontSize: 13,
                                          color: color.AppColors.secondary),
                                    ))
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    Container(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(12),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisSize: MainAxisSize.max,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                const SizedBox(
                                  width: 145,
                                  child: Text(
                                    "Plan de pago",
                                    style: TextStyle(
                                        fontSize: 13, color: Colors.black),
                                  ),
                                ),
                                const Spacer(),
                                SizedBox(
                                    width: 180,
                                    child: DropdownButton<String>(
                                      items: paymentPlan.map((String value) {
                                        return DropdownMenuItem<String>(
                                          value: value,
                                          child: Text(
                                            value,
                                            style:
                                                const TextStyle(fontSize: 14),
                                          ),
                                        );
                                      }).toList(),
                                      onChanged: (value) {
                                        setState(() {
                                          paymentPlanDropdownValue =
                                              value.toString();
                                        });
                                      },
                                      value: paymentPlanDropdownValue.isNotEmpty
                                          ? paymentPlanDropdownValue
                                          : null,
                                      isExpanded: true,
                                    ))
                              ],
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            Row(
                              mainAxisSize: MainAxisSize.max,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                const SizedBox(
                                  width: 200,
                                  child: Text(
                                    "Cargos totales",
                                    style: TextStyle(
                                        fontSize: 13, color: Colors.black),
                                  ),
                                ),
                                const Spacer(),
                                SizedBox(
                                    width: 95,
                                    child: Text(
                                      '$totalCharges MXN ',
                                      style: const TextStyle(
                                          fontSize: 13,
                                          color: color.AppColors.secondary),
                                    ))
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    Container(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(12),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisSize: MainAxisSize.max,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                const SizedBox(
                                  width: 170,
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Text(
                                        "Clabe",
                                        style: TextStyle(
                                            fontSize: 13, color: Colors.black),
                                      ),
                                      Text(
                                        "Cuenta del préstamo",
                                        style: TextStyle(
                                            fontSize: 11,
                                            color: color.AppColors.secondary),
                                      )
                                    ],
                                  ),
                                ),
                                const Spacer(),
                                SizedBox(
                                    width: 150,
                                    child: DropdownButton<String>(
                                      items: accounts.map((String value) {
                                        return DropdownMenuItem<String>(
                                          value: value,
                                          child: Text(
                                            value,
                                            style:
                                                const TextStyle(fontSize: 14),
                                          ),
                                        );
                                      }).toList(),
                                      onChanged: (value) {
                                        setState(() {
                                          accountDropdownValue =
                                              value.toString();
                                        });
                                      },
                                      value: accountDropdownValue.isNotEmpty
                                          ? accountDropdownValue
                                          : null,
                                      isExpanded: true,
                                    ))
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    Container(
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(12),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisSize: MainAxisSize.max,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                const SizedBox(
                                  width: 150,
                                  child: Text(
                                    "Motivo del crédito",
                                    style: TextStyle(
                                        fontSize: 13, color: Colors.black),
                                  ),
                                ),
                                const Spacer(),
                                SizedBox(
                                    width: 170,
                                    child: DropdownButton<String>(
                                      items: reasons.map((String value) {
                                        return DropdownMenuItem<String>(
                                          value: value,
                                          child: Text(
                                            value,
                                            style:
                                                const TextStyle(fontSize: 14),
                                          ),
                                        );
                                      }).toList(),
                                      onChanged: (value) {
                                        setState(() {
                                          reasonsDropdownValue =
                                              value.toString();
                                        });
                                      },
                                      value: reasonsDropdownValue,
                                      isExpanded: true,
                                    ))
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    Row(
                      children: [
                        Checkbox(
                          value: checkedContract,
                          onChanged: (bool? newValue) {
                            setState(() {
                              checkedContract = newValue ?? false;
                            });
                          },
                        ),
                        Flexible(
                            child: RichText(
                          text: const TextSpan(
                            text:
                                "Acepto la CARATULA DEL CONTRATO DEL CRÉDITO REVOLVENTE.",
                            style: TextStyle(
                              color: color.AppColors.contractText,
                              decoration: TextDecoration.underline,
                            ),
                          ),
                        ))
                      ],
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    FilledButton(
                      onPressed: _formKey.currentState?.validate() ?? false
                          ? () {
                              requestLoan(context);
                            }
                          : null,
                      style: const ButtonStyle().copyWith(
                        backgroundColor: MaterialStateProperty.all(
                          (_formKey.currentState?.validate() ?? false) &&
                                  checkedContract
                              ? null
                              : Colors.grey.shade300,
                        ),
                      ),
                      child: const Text('SOLICITAR'),
                    ),
                  ],
                ),
              )),
        ),
      ),
    );
  }

  requestLoan(BuildContext context) async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();

    var today = DateTime.now();
    var dateFormat = DateFormat('dd-MM-yyyy');
    var dateFormatTime = DateFormat('dd-MM-yyyy hh:mm');
    String dateStr = dateFormat.format(today);

    final userId = prefs.getInt('userId');
    var accountId = 0;

    for (var i = 0; i < accounts.length; i++) {
      var accountStr = accounts[i];
      var accountInfo = accountsInfo[i];

      if (accountStr == '${accountInfo["bank"]} / (${accountInfo["number"]})') {
        accountId = accountInfo['_id'];
      }
    }

    var comAux = com;
    var period = '';
    if (selectedButton == 0) {
      comAux = com;
      period = "Quincenal";
    } else if (selectedButton == 1) {
      comAux = 28;
      period = "Mensual";
    } else {
      comAux = 33;
      period = "Bimestral";
    }

    final info = {
      'user_id': userId,
      'name': "Credito $dateStr",
      'amount': amountController.text,
      'cat': comAux,
      'period_payment': period,
      'number_payment': int.parse(numPaymentsDropdownValue),
      'payment_plan': paymentPlanDropdownValue,
      'total_charges': totalChargesValue.toString(),
      'limit_date': paymentFirstimitDate,
      'account_transfer': accountId,
      'reason': reasonsDropdownValue,
      'requested_date': dateStr,
      'status': 'active'
    };

    final transactionRequest = {
      'user_id': userId,
      'name': 'Solicitud de prestamo: "Credito $dateStr',
      'date': dateFormatTime.format(DateTime.now())
    };

    final transactionRequestAccepted = {
      'user_id': userId,
      'name': 'Perestamo fue aceptado: "Credito $dateStr',
      'date':
          dateFormatTime.format(DateTime.now().add(const Duration(minutes: 15)))
    };

    try {
      final result = await dbHelper.insertInfo('loans', info);
      await dbHelper.insertInfo('transactions', transactionRequest);
      await dbHelper.insertInfo('transactions', transactionRequestAccepted);

      final loans = await dbHelper.getLastLoans(userId ?? 0);

      if (result > 0) {
        for (var i = 0; i < int.parse(numPaymentsDropdownValue); i++) {
          await addPayments(loans[0]['_id'], userId ?? 0, (i + 1));
        }
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Solicitud realizada correctamente!'),
          ),
        );
        amountController.clear();
        Navigator.pop(context);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('¡Datos incorrectos!'),
          ),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('¡Datos incorrectos!'),
        ),
      );
    }
  }

  Future<void> addPayments(int loanId, int userId, int paymentNumber) async {
    var daysToAdd = 15;

    var comAux = com;
    if (selectedButton == 0) {
      daysToAdd = 15;
      comAux = com;
    } else if (selectedButton == 1) {
      daysToAdd = 30;
      comAux = 28;
    } else {
      daysToAdd = 61;
      comAux = 33;
    }
    var today = DateTime.now();
    var dateFormat = DateFormat('dd-MM-yyyy');
    String dateStr =
        dateFormat.format(today.add(Duration(days: daysToAdd * paymentNumber)));

    final totalAmount = double.parse(globalAmount);

    var calcTotalCharges = (comAux * totalAmount) / 100;

    final paymentByPeriod =
        (totalAmount + calcTotalCharges) / int.parse(numPaymentsDropdownValue);
    try {
      final payment = {
        'user_id': userId,
        'load_id': loanId,
        'amount': paymentByPeriod.toString(),
        'number': paymentNumber,
        'limit_date': dateStr,
      };
      await dbHelper.insertInfo('payments', payment);
    } catch (e) {}
  }
}
