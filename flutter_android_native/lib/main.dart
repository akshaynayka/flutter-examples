import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('com.example.flutter_android_native');

  String? _batteryString = 'Press the button to get battery percentage';
  Future<String?> _getBatteryPercentage() async {
    String batteryPercentage;
    try {
      final int result = await platform.invokeMethod('getBatteryPercentage');
      batteryPercentage = 'Battery at $result %';
    } on PlatformException catch (error) {
      batteryPercentage = "Failed to get battery level: '${error.message}'";
    }
    return batteryPercentage;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              _batteryString!,
            ),
            const SizedBox(height: 50),
            ElevatedButton(
              onPressed: () async {
                _batteryString = await _getBatteryPercentage();
                setState(() {});
              },
              child: const Text('Get Battery Percentage'),
            ),
          ],
        ),
      ),
    );
  }
}
