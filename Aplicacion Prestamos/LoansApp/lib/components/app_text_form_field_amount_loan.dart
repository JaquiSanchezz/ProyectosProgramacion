import 'package:flutter/material.dart';
import 'package:loans_app/values/app_colors.dart' as color;

class AppTextFormFieldAmountLoan extends StatelessWidget {
  const AppTextFormFieldAmountLoan({
    required this.textInputAction,
    required this.labelText,
    required this.hintText,
    required this.keyboardType,
    required this.controller,
    super.key,
    this.onChanged,
    this.onTap,
    this.validator,
    this.obscureText,
    this.suffixIcon,
    this.onEditingComplete,
    this.autofocus,
    this.focusNode,
  });

  final void Function(String)? onChanged;
  final void Function()? onTap;
  final String? Function(String?)? validator;
  final TextInputAction textInputAction;
  final TextInputType keyboardType;
  final TextEditingController controller;
  final bool? obscureText;
  final Widget? suffixIcon;
  final String labelText;
  final String hintText;
  final bool? autofocus;
  final FocusNode? focusNode;
  final void Function()? onEditingComplete;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(0),
      child: TextFormField(
        controller: controller,
        keyboardType: keyboardType,
        textInputAction: textInputAction,
        focusNode: focusNode,
        onChanged: onChanged,
        autofocus: autofocus ?? false,
        validator: validator,
        obscureText: obscureText ?? false,
        textAlign: TextAlign.center,
        obscuringCharacter: '*',
        onEditingComplete: onEditingComplete,
        decoration: InputDecoration(
          border: InputBorder.none,
          suffixIcon: suffixIcon,
          labelText: labelText,
          fillColor: color.AppColors.backgroundColor,
          floatingLabelBehavior: FloatingLabelBehavior.never,
        ),
        onTap: onTap,
        onTapOutside: (event) => FocusScope.of(context).unfocus(),
        style: const TextStyle(
          fontWeight: FontWeight.w500,
          color: Colors.black,
        ),
      ),
    );
  }
}
