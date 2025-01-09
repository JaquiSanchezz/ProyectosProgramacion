import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:loans_app/ui/accounts/add/add_account_screen.dart';
import 'package:loans_app/ui/home/home_screen.dart';
import 'package:loans_app/ui/loans/request/request_loan_screen.dart';
import 'package:loans_app/ui/register/fulldata/fulldata_page.dart';
import 'package:loans_app/utils/database_helper.dart';
import 'package:loans_app/values/app_constants.dart';
import 'package:loans_app/values/app_routes.dart';
import 'package:loans_app/values/app_theme.dart';

import 'ui/login/login_page.dart';
import 'ui/register/register_page.dart';

final dbHelper = DatabaseHelper();

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await dbHelper.init();
  SystemChrome.setSystemUIOverlayStyle(
    const SystemUiOverlayStyle(
      statusBarIconBrightness: Brightness.light,
    ),
  );

  SystemChrome.setPreferredOrientations(
    [DeviceOrientation.portraitUp],
  ).then(
    (_) => runApp(
      const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Loans',
      theme: AppTheme.themeData,
      initialRoute: AppRoutes.loginScreen,
      navigatorKey: AppConstants.navigationKey,
      routes: {
        AppRoutes.loginScreen: (context) => const LoginPage(),
        AppRoutes.registerScreen: (context) => const RegisterPage(),
        AppRoutes.registerFullData: (context) => const FullDataPage(),
        AppRoutes.homeScreen: (context) => const TabHomeScreen(),
        AppRoutes.requestLoan: (context) => const RequestLoanScreen(),
        AppRoutes.addAccount: (context) => const AddAccountScreen()
      },
    );
  }
}
