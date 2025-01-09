import 'package:flutter/material.dart';
import 'package:loans_app/utils/database_helper.dart';
import 'package:loans_app/values/app_colors.dart' as color;
import 'package:shared_preferences/shared_preferences.dart';

final dbHelper = DatabaseHelper();

class TransactionsScreen extends StatefulWidget {
  const TransactionsScreen({super.key});

  @override
  State<TransactionsScreen> createState() => _TransactionsScreenState();
}

class _TransactionsScreenState extends State<TransactionsScreen> {
  List<Map<String, dynamic>> items = [];

  @override
  void initState() {
    super.initState();
    getTransactions();
  }

  Future<void> getTransactions() async {
    await dbHelper.init();
    final SharedPreferences prefs = await SharedPreferences.getInstance();

    final id = prefs.getInt('userId');
    final result = await dbHelper.getInfoByUser('transactions', id ?? 0);

    setState(() {
      items = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: color.AppColors.backgroundColor,
      appBar: AppBar(
        title: const Text(
          'Movimientos',
          style: TextStyle(color: Colors.white),
        ),
        forceMaterialTransparency: false,
        automaticallyImplyLeading: false,
        backgroundColor: color.AppColors.accentColor,
      ),
      body: SafeArea(
        child: RefreshIndicator(
            onRefresh: getTransactions,
            child: ListView.builder(
                shrinkWrap: true,
                padding:
                    const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
                itemCount: items.length,
                itemBuilder: (context, index) {
                  return Container(
                    height: 65,
                    margin: const EdgeInsets.only(bottom: 12),
                    padding: const EdgeInsets.symmetric(horizontal: 20),
                    decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(12)),
                    child: Padding(
                      padding: const EdgeInsets.only(top: 10, bottom: 10),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
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
                                      color: color.AppColors.disableColor),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                })),
      ),
    );
  }
}
