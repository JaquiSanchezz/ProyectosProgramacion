// ignore_for_file: use_build_context_synchronously

import 'dart:io';
import 'dart:math';
import 'package:flutter/cupertino.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter/material.dart';
import 'package:loans_app/components/app_text_form_field.dart';
import 'package:loans_app/ui/login/login_page.dart';
import 'package:loans_app/utils/database_helper.dart';
import 'package:loans_app/utils/extensions.dart';
import 'package:loans_app/values/app_colors.dart';
import 'package:intl/intl.dart';
import 'package:loans_app/values/app_routes.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FullDataPage extends StatefulWidget {
  const FullDataPage({Key? key}) : super(key: key);

  @override
  FullDataPageState createState() => FullDataPageState();
}

class FullDataPageState extends State<FullDataPage> {
  int currentStep = 0;
  final dbHelper = DatabaseHelper();
  final _formKeyPersonalData = GlobalKey<FormState>();
  TextEditingController birthdayController = TextEditingController();
  TextEditingController educationController = TextEditingController();
  File? _image = null;
  final picker = ImagePicker();

  Future getImageFromGallery() async {
    final pickedFile = await picker.pickImage(source: ImageSource.gallery);
    setState(() {
      if (pickedFile != null) {
        _image = File(pickedFile.path);
      }
    });
  }

 
  Future getImageFromCamera() async {
    final pickedFile = await picker.pickImage(source: ImageSource.camera);

    setState(() {
      if (pickedFile != null) {
        _image = File(pickedFile.path);
      }
    });
  }

  Future showOptions() async {
    showCupertinoModalPopup(
      context: context,
      builder: (context) => CupertinoActionSheet(
        actions: [
          CupertinoActionSheetAction(
            child: const Text('Galeria'),
            onPressed: () {
             
              Navigator.of(context).pop();
             
              getImageFromGallery();
            },
          ),
          CupertinoActionSheetAction(
            child: const Text('Camara'),
            onPressed: () {
              
              Navigator.of(context).pop();
              
              getImageFromCamera();
            },
          ),
        ],
      ),
    );
  }

  final _formKeyLaboralData = GlobalKey<FormState>();

  TextEditingController currentWorkController = TextEditingController();
  TextEditingController earningsController = TextEditingController();

  final _formKeyAccountInfo = GlobalKey<FormState>();
  TextEditingController accountNumberController = TextEditingController();
  TextEditingController accountNameController = TextEditingController();

  List<String> list = <String>['Modalidad', 'Tiempo Completo', 'Medio tiempo'];
  String dropdownValue = 'Modalidad';

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
    final size = context.mediaQuerySize;
    return MaterialApp(
      home: Scaffold(
          body: SingleChildScrollView(
        child: Container(
            padding: const EdgeInsets.all(0),
            child: Column(
              children: [
                Container(
                  height: size.height * 0.25,
                  width: double.infinity,
                  padding: const EdgeInsets.all(20),
                  decoration: const BoxDecoration(
                    gradient: LinearGradient(
                      colors: [
                        AppColors.lightBlue,
                        AppColors.blue,
                        AppColors.darkBlue,
                      ],
                    ),
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(
                          top: 15,
                        ),
                        child: IconButton(
                          onPressed: () => Navigator.pop(context),
                          icon: const Icon(
                            Icons.arrow_back_ios,
                            color: Colors.white,
                          ),
                        ),
                      ),
                      Column(
                        children: [
                          Text(
                            'Completar registro',
                            style: Theme.of(context).textTheme.titleLarge,
                          ),
                          const SizedBox(
                            height: 6,
                          ),
                          Text(
                            'Llena los datos para completar tu registro',
                            style: Theme.of(context).textTheme.bodySmall,
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
                Stepper(
                  controlsBuilder: (context, _) {
                    return Row(
                      children: <Widget>[
                        FilledButton(
                          onPressed: () async {
                            if (currentStep == 0) {
                              if (_formKeyPersonalData.currentState
                                      ?.validate() ??
                                  false) {
                                bool isLastStep =
                                    (currentStep == getSteps().length - 1);
                                if (isLastStep) {
                                  
                                } else {
                                  setState(() {
                                    currentStep += 1;
                                  });
                                }
                              }
                            } else if (currentStep == 1) {
                              if (_formKeyLaboralData.currentState
                                      ?.validate() ??
                                  false) {
                                bool isLastStep =
                                    (currentStep == getSteps().length - 1);
                                if (isLastStep) {
                                  
                                } else {
                                  setState(() {
                                    currentStep += 1;
                                  });
                                }
                              }
                            } else if (currentStep == 2) {
                              if ((_formKeyLaboralData.currentState
                                          ?.validate() ??
                                      false) &&
                                  (_formKeyPersonalData.currentState
                                          ?.validate() ??
                                      false) &&
                                  (_formKeyLaboralData.currentState
                                          ?.validate() ??
                                      false)) {
                                
                                final SharedPreferences prefs =
                                    await SharedPreferences.getInstance();
                                final String? name = prefs.getString('name');
                                final String? lastname =
                                    prefs.getString('last_name');

                                final String? email = prefs.getString('email');

                                final String? password =
                                    prefs.getString('password');

                                final intValue = Random().nextInt(10000) + 5000;

                                final Map<String, dynamic> info = {
                                  'name': name,
                                  'last_name': lastname,
                                  'email': email,
                                  'password': password,
                                  'birthdate': birthdayController.text,
                                  'education': educationController.text,
                                  'ine': '',
                                  'work': currentWorkController.text,
                                  'earnings': earningsController.text,
                                  'modality_work': dropdownValue,
                                  'max_loan_amount': intValue.toString()
                                };

                                try {
                                  final res =
                                      await dbHelper.insertInfo("users", info);

                                  if (res > 0) {
                                    final infoUser = await dbHelper
                                        .getCurrentUserInfo(email.toString());

                                    if (infoUser.isNotEmpty) {
                                      await prefs.setInt(
                                          'userId', infoUser[0]['_id']);
                                      await prefs.setString(
                                          'email', email.toString());
                                      await prefs.setString('max_loan_amount',
                                          infoUser[0]['max_loan_amount']);

                                      ScaffoldMessenger.of(context)
                                          .showSnackBar(
                                        const SnackBar(
                                          content: Text('¡Registro completo!'),
                                        ),
                                      );
                                      AppRoutes.loginScreen.pushName();
                                    } else {
                                      ScaffoldMessenger.of(context)
                                          .showSnackBar(
                                        const SnackBar(
                                          content: Text('¡Datos incorrectos!'),
                                        ),
                                      );
                                    }

                                  
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
                            }
                          },
                          style: const ButtonStyle().copyWith(),
                          child: const Text('Continuar'),
                        ),
                        TextButton(
                          onPressed: () => currentStep == 0
                              ? null
                              : setState(() {
                                  currentStep -= 1;
                                }),
                          child: const Text(
                            'Cancelar',
                            style: TextStyle(color: Colors.blue),
                          ),
                        ),
                      ],
                    );
                  },
                  type: StepperType.vertical,
                  currentStep: currentStep,
                  onStepTapped: (step) => setState(() {
                    currentStep = step;
                  }),
                  steps: getSteps(),
                )
              ],
            )),
      )),
    );
  }

  List<Step> getSteps() {
    return <Step>[
      Step(
        state: currentStep > 0 ? StepState.complete : StepState.indexed,
        isActive: currentStep >= 0,
        title: const Text("Información personal"),
        content: Column(
          children: [
            Form(
                key: _formKeyPersonalData,
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 10, vertical: 10),
                      child: Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            AppTextFormField(
                              labelText: 'Fecha de nacimiento',
                              autofocus: true,
                              keyboardType: TextInputType.name,
                              textInputAction: TextInputAction.next,
                              onTap: () async {
                                DateTime? pickedDate = await showDatePicker(
                                    context: context,
                                    initialDate: DateTime.now(),
                                    firstDate: DateTime(1940, 1,
                                        1), 
                                    lastDate: DateTime(2101));

                                if (pickedDate != null) {
                                  String formattedDate =
                                      DateFormat('yyyy-MM-dd')
                                          .format(pickedDate);

                                  setState(() {
                                    birthdayController.text = formattedDate;
                                  });
                                }
                              },
                              onChanged: (value) =>
                                  _formKeyPersonalData.currentState?.validate(),
                              validator: (value) {
                                return value!.isEmpty
                                    ? 'Por favor, introduzca su fecha de nacimiento '
                                    : value.length < 3
                                        ? 'Fecha de nacimiento inválida'
                                        : null;
                              },
                              controller: birthdayController,
                            ),
                            AppTextFormField(
                              labelText: 'Educación',
                              autofocus: true,
                              keyboardType: TextInputType.name,
                              textInputAction: TextInputAction.next,
                              onChanged: (value) =>
                                  _formKeyPersonalData.currentState?.validate(),
                              validator: (value) {
                                return value!.isEmpty
                                    ? 'Por favor, introduzca su último grado de estudios '
                                    : value.length < 4
                                        ? 'Grado de estudios inválido'
                                        : null;
                              },
                              controller: educationController,
                            ),
                            Row(
                              children: [
                                TextButton(
                                    onPressed: () => {showOptions()},
                                    child: const Text("INE")),
                                Center(
                                  child: _image == null
                                      ? const Text(
                                          'No haz seleccionado una imagen.')
                                      : Image.file(
                                          _image!,
                                          width: 120,
                                          height: 60,
                                        ),
                                )
                              ],
                            )
                          ]),
                    ),
                  ],
                ))
          ],
        ),
      ),
      Step(
        state: currentStep > 1 ? StepState.complete : StepState.indexed,
        isActive: currentStep >= 1,
        title: const Text("Información laboral"),
        content: Column(
          children: [
            Form(
                key: _formKeyLaboralData,
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 10, vertical: 10),
                      child: Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            AppTextFormField(
                              labelText: 'Trabajo actual',
                              autofocus: true,
                              keyboardType: TextInputType.name,
                              textInputAction: TextInputAction.next,
                              onChanged: (value) =>
                                  _formKeyLaboralData.currentState?.validate(),
                              validator: (value) {
                                return value!.isEmpty
                                    ? 'Por favor, introduzca su trabajo actual '
                                    : value.length < 4
                                        ? 'Trabajo actual inválido'
                                        : null;
                              },
                              controller: currentWorkController,
                            ),
                            AppTextFormField(
                              labelText: 'Ingresos',
                              autofocus: true,
                              keyboardType: TextInputType.number,
                              textInputAction: TextInputAction.next,
                              onChanged: (value) =>
                                  _formKeyPersonalData.currentState?.validate(),
                              validator: (value) {
                                return value!.isEmpty
                                    ? 'Por favor, introduzca sus ingresos estimados '
                                    : value.length < 4
                                        ? 'Ingresos estimados inválidos'
                                        : null;
                              },
                              controller: earningsController,
                            ),
                            SizedBox(
                              width: double.infinity,
                              child: DropdownMenu<String>(
                                initialSelection: list.first,
                                width: 310,
                                onSelected: (String? value) {
                                  dropdownValue = value.toString();
                                },
                                dropdownMenuEntries: list
                                    .map<DropdownMenuEntry<String>>(
                                        (String value) {
                                  return DropdownMenuEntry<String>(
                                      value: value, label: value);
                                }).toList(),
                              ),
                            )
                          ]),
                    ),
                  ],
                ))
          ],
        ),
      ),
      Step(
        state: currentStep > 2 ? StepState.complete : StepState.indexed,
        isActive: currentStep >= 2,
        title: const Text("Información bancaria"),
        content: Column(
          children: [
            Form(
                key: _formKeyAccountInfo,
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 10, vertical: 10),
                      child: Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            AppTextFormField(
                              labelText: 'Número de cuenta',
                              autofocus: true,
                              keyboardType: TextInputType.number,
                              textInputAction: TextInputAction.next,
                              onChanged: (value) =>
                                  _formKeyAccountInfo.currentState?.validate(),
                              validator: (value) {
                                return value!.isEmpty
                                    ? 'Por favor, introduzca el número de cuenta '
                                    : value.length < 3
                                        ? 'Número inválido'
                                        : null;
                              },
                              controller: accountNumberController,
                            ),
                            AppTextFormField(
                              labelText: 'Nombre del propietario',
                              autofocus: true,
                              keyboardType: TextInputType.name,
                              textInputAction: TextInputAction.next,
                              onChanged: (value) =>
                                  _formKeyAccountInfo.currentState?.validate(),
                              validator: (value) {
                                return value!.isEmpty
                                    ? 'Por favor, introduzca el nombre del propietario de la cuenta '
                                    : value.length < 3
                                        ? 'Nombre inválido'
                                        : null;
                              },
                              controller: accountNameController,
                            ),
                          ]),
                    ),
                  ],
                ))
          ],
        ),
      ),
    ];
  }
}
