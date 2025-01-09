// ignore_for_file: use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:loans_app/components/app_text_form_field.dart';
import 'package:loans_app/components/app_text_form_field_without_border.dart';
import 'package:loans_app/utils/database_helper.dart';
import 'package:loans_app/values/app_colors.dart' as color;
import 'package:shared_preferences/shared_preferences.dart';

class AddAccountScreen extends StatefulWidget {
  const AddAccountScreen({super.key});

  @override
  State<AddAccountScreen> createState() => AddAccountState();
}

final dbHelper = DatabaseHelper();

class AddAccountState extends State<AddAccountScreen> {
  final _formKey = GlobalKey<FormState>();
  TextEditingController accountNumberController = TextEditingController();
  TextEditingController ownerAccountController = TextEditingController();
  TextEditingController aliasAccountController = TextEditingController();

  List<String> banks = <String>['BANAMEX', 'SANTANDER', 'BBVA'];
  String banksDropdownValue = 'BANAMEX';

  @override
  void initState() {
    super.initState();
    initDB();
  }

  Future<void> initDB() async {
    await dbHelper.init();
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
          'Nueva cuenta',
          style: TextStyle(color: Colors.white, fontSize: 20),
        ),
        forceMaterialTransparency: false,
        backgroundColor: color.AppColors.accentColor,
      ),
      body: SafeArea(
        child: SingleChildScrollView(
            child: Padding(
          padding: const EdgeInsets.all(20),
          child: Form(
              key: _formKey,
              child: Column(
                children: [
                  const Text(
                      "Escribe el número de Tarjeta, Cuenta, CLABE para transferir a una nueva cuenta,"),
                  Container(
                    width: double.infinity,
                    decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(12)),
                    margin: const EdgeInsets.only(top: 20),
                    child: Padding(
                      padding: const EdgeInsets.all(14),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text("Ingresa el número"),
                          AppTextFormFieldWithOutBorder(
                            autofocus: true,
                            labelText: '',
                            hintText: "Ej: 12345",
                            keyboardType: TextInputType.number,
                            textInputAction: TextInputAction.next,
                            onChanged: (value) {
                              _formKey.currentState?.validate();
                            },
                            validator: (value) {
                              return value!.isEmpty
                                  ? 'Por favor, introduzca el número de Tarjeta, Cuenta o CLABE.'
                                  : value.length > 8
                                      ? null
                                      : 'Número inválido';
                            },
                            controller: accountNumberController,
                          ),
                        ],
                      ),
                    ),
                  ),
                  Padding(
                    padding:
                        const EdgeInsets.symmetric(vertical: 35, horizontal: 6),
                    child: Column(
                      children: [
                        Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("Ingresa el número"),
                            SizedBox(
                                width: double.infinity,
                                child: DropdownButton<String>(
                                  items: banks.map((String value) {
                                    return DropdownMenuItem<String>(
                                      value: value,
                                      child: Text(
                                        value,
                                        style: const TextStyle(fontSize: 14),
                                      ),
                                    );
                                  }).toList(),
                                  onChanged: (value) {
                                    setState(() {
                                      banksDropdownValue = value.toString();
                                    });
                                    banksDropdownValue = value.toString();
                                  },
                                  value: banksDropdownValue,
                                  isExpanded: true,
                                )),
                          ],
                        ),
                        const SizedBox(
                          height: 30,
                        ),
                        AppTextFormField(
                          labelText: 'Nombre y apellidos',
                          hintText: "del titular de la cuenta",
                          keyboardType: TextInputType.emailAddress,
                          textInputAction: TextInputAction.next,
                          onChanged: (value) {
                            _formKey.currentState?.validate();
                          },
                          validator: (value) {
                            return value!.isEmpty
                                ? 'Por favor, introduzca su nombre y apellidos'
                                : value.length > 10
                                    ? null
                                    : 'Nombre y apellidos inválidos';
                          },
                          controller: ownerAccountController,
                        ),
                        AppTextFormField(
                          labelText: 'Alias',
                          hintText: "del titular de la cuenta",
                          keyboardType: TextInputType.emailAddress,
                          textInputAction: TextInputAction.next,
                          onChanged: (value) {
                            _formKey.currentState?.validate();
                          },
                          validator: (value) {
                            return value!.isEmpty
                                ? 'Por favor, introduzca el alias'
                                : value.length >= 4
                                    ? null
                                    : 'Alias inválido';
                          },
                          controller: aliasAccountController,
                        ),
                        FilledButton(
                          onPressed: () async {
                            final SharedPreferences prefs =
                                await SharedPreferences.getInstance();

                            if (accountNumberController.text.isNotEmpty &&
                                ownerAccountController.text.isNotEmpty &&
                                aliasAccountController.text.isNotEmpty) {
                              final userId = prefs.getInt('userId');
                              final info = {
                                'user_id': userId ?? 0,
                                'number': accountNumberController.text,
                                'bank': banksDropdownValue,
                                'fullname': ownerAccountController.text,
                                'alias': aliasAccountController.text
                              };

                              try {
                                final result =
                                    await dbHelper.insertInfo('accounts', info);

                                if (result > 0) {
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    const SnackBar(
                                      content: Text(
                                          'Cuenta agregada correctamente!'),
                                    ),
                                  );
                                  accountNumberController.clear();
                                  ownerAccountController.clear();
                                  aliasAccountController.clear();
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
                            } else {
                              ScaffoldMessenger.of(context).showSnackBar(
                                const SnackBar(
                                  content:
                                      Text('¡Debes llenar todos los campos!'),
                                ),
                              );
                            }
                          },
                          style: const ButtonStyle().copyWith(
                            shape: MaterialStateProperty.all(
                              RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                            ),
                          ),
                          child: const Text('AGREGAR'),
                        ),
                      ],
                    ),
                  )
                ],
              )),
        )),
      ),
    );
  }
}
