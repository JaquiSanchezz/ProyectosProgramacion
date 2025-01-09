import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path_provider/path_provider.dart';

class DatabaseHelper {
  static const _databaseName = "loans.db";
  static const _databaseVersion = 1;

  static const columnId = '_id';

  static const userTable = 'users';
  static const userColumnName = 'name';
  static const userColumnLastName = 'last_name';
  static const userColumnEmail = 'email';
  static const userColumnPassword = 'password';
  static const userColumnBirthdate = 'birthdate';
  static const userColumnEducation = 'education';
  static const userColumnINE = 'ine';
  static const userColumnCurrentWork = 'work';
  static const userColumnEarnings = 'earnings';
  static const userColumnModalityWork = 'modality_work';
  static const userColumnMaxLoan = 'max_loan_amount';

  static const accountsTable = 'accounts';
  static const columnUserId = 'user_id';
  static const accountsColumnNumber = 'number';
  static const accountsColumnBank = 'bank';
  static const accountsColumnFullname = 'fullname';
  static const accountsColumnAlias = 'alias';

  static const loansTable = 'loans';
  static const loansColumnName = 'name';
  static const loansColumnAmount = 'amount';
  static const loansColumnCat = 'cat';
  static const loansColumnPeriodPayment = 'period_payment';
  static const loansColumnNumberPayments = 'number_payment';
  static const loansColumnPaymentPlan = 'payment_plan';
  static const loansColumnTotalCharges = 'total_charges';
  static const loansColumnLimitDatePayment = 'limit_date';
  static const loansColumnAccountTransfer = 'account_transfer';
  static const loansColumnReason = 'reason';
  static const loansColumnRequestedDate = 'requested_date';
  static const loansColumnStatus = 'status';

  static const paymentsTable = 'payments';
  static const paymentsColumnLoan = 'load_id';
  static const paymentsColumnAmount = 'amount';
  static const paymentsColumnNumber = 'number';
  static const paymentsColumnLimitDate = 'limit_date';
  static const paymentsColumnPaymentDate = 'payment_date';

  static const transactionsTable = 'transactions';
  static const transactionsColumnName = 'name';
  static const transactionsColumnDate = 'date';

  late Database _db;

  Future<void> init() async {
    final documentsDirectory = await getApplicationDocumentsDirectory();
    final path = join(documentsDirectory.path, _databaseName);
    _db = await openDatabase(
      path,
      version: _databaseVersion,
      onCreate: _onCreate,
    );
  }

 
  Future _onCreate(Database db, int version) async {
    await db.execute('''
          CREATE TABLE $userTable (
             $columnId INTEGER PRIMARY KEY autoincrement,
             $userColumnName TEXT NOT NULL,
             $userColumnLastName TEXT NOT NULL,
             $userColumnEmail varchar(255) NOT NULL UNIQUE,
             $userColumnPassword TEXT NOT NULL,
             $userColumnBirthdate TEXT NOT NULL,
             $userColumnEducation TEXT NOT NULL,
             $userColumnINE TEXT NOT NULL,
             $userColumnCurrentWork TEXT NOT NULL,
             $userColumnEarnings TEXT NOT NULL,
             $userColumnModalityWork TEXT NOT NULL,
             $userColumnMaxLoan TEXT NOT NULL
          )  ''');

    await db.execute('''
            CREATE TABLE $accountsTable (
              $columnId INTEGER PRIMARY KEY autoincrement,
              $columnUserId int(11) NOT NULL,
              $accountsColumnNumber TEXT NOT NULL,
              $accountsColumnBank TEXT NOT NULL,
              $accountsColumnFullname TEXT NOT NULL,
              $accountsColumnAlias TEXT NOT NULL
         )
            ''');

    await db.execute('''
            CREATE TABLE $loansTable (
              $columnId INTEGER PRIMARY KEY autoincrement,
              $columnUserId int(11) NOT NULL,
              $loansColumnName TEXT NOT NULL,
              $loansColumnAmount TEXT NOT NULL,
              $loansColumnCat TEXT NOT NULL,
              $loansColumnPeriodPayment TEXT NOT NULL,
              $loansColumnNumberPayments int(11) NOT NULL,
              $loansColumnPaymentPlan TEXT NOT NULL,
              $loansColumnTotalCharges TEXT NOT NULL,
              $loansColumnLimitDatePayment DATE NOT NULL,
              $loansColumnAccountTransfer int(11) NOT NULL,
              $loansColumnReason TEXT NOT NULL,
              $loansColumnRequestedDate DATE NOT NULL,
              $loansColumnStatus TEXT NOT NULL
          )
            ''');

    await db.execute('''
            CREATE TABLE $paymentsTable (
              $columnId INTEGER PRIMARY KEY autoincrement,
              $columnUserId int(11) NOT NULL,
              $paymentsColumnLoan int(11) NOT NULL,
              $paymentsColumnAmount TEXT NOT NULL,
              $paymentsColumnNumber int(11) NOT NULL,
              $paymentsColumnLimitDate DATE NOT NULL,
              $paymentsColumnPaymentDate DATETIME  NULL
          )
            ''');

    await db.execute('''
            CREATE TABLE $transactionsTable (
              $columnId INTEGER PRIMARY KEY autoincrement,
              $columnUserId int(11) NOT NULL,
              $transactionsColumnName TEXT NOT NULL,
              $transactionsColumnDate DATETIME NOT NULL
          )
            ''');
  }

  Future<List<Map<String, dynamic>>> login(
      String email, String password) async {
    return await _db.rawQuery(
        'SELECT * FROM $userTable WHERE email = "$email" AND password = "$password"');
  }

  Future<List<Map<String, dynamic>>> getCurrentUserInfo(String email) async {
    return await _db
        .rawQuery('SELECT * FROM $userTable WHERE email = "$email"');
  }

  Future<List<Map<String, dynamic>>> getLoansByStatus(
      String status, int id) async {
    return await _db.rawQuery(
        'SELECT * FROM $loansTable WHERE status = "$status" AND user_id = $id  ORDER BY _id DESC');
  }

  Future<List<Map<String, dynamic>>> getPendingPayments(
    int loanId,
  ) async {
    return await _db.rawQuery(
        'SELECT * FROM $paymentsTable WHERE  _id = $loanId AND payment_date IS NULL  ORDER BY _id DESC');
  }

  Future<List<Map<String, dynamic>>> getLastLoans(int id) async {
    return await _db.rawQuery(
        'SELECT * FROM $loansTable WHERE user_id = $id ORDER BY _id DESC');
  }

  Future<List<Map<String, dynamic>>> getInfoByUser(String table, int id) async {
    return await _db.rawQuery(
        'SELECT * FROM $table WHERE user_id = $id  ORDER BY _id DESC');
  }

  Future<int> insertInfo(String table, Map<String, dynamic> row) async {
    return await _db.insert(table, row);
  }

  Future<List<Map<String, dynamic>>> queryAllRows(
    String table,
  ) async {
    return await _db.query(table);
  }

  Future<int> queryRowCount(
    String table,
  ) async {
    final results = await _db.rawQuery('SELECT COUNT(*) FROM $table');
    return Sqflite.firstIntValue(results) ?? 0;
  }

  Future<int> update(String table, Map<String, dynamic> row) async {
    int id = row[columnId];
    return await _db.update(
      table,
      row,
      where: '$columnId = ?',
      whereArgs: [id],
    );
  }

  Future<int> delete(String table, int id) async {
    return await _db.delete(
      table,
      where: '$columnId = ?',
      whereArgs: [id],
    );
  }
}
