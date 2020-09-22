<div align="center">
<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_5.jpg" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_6.jpg" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_7.jpg" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_8.jpg" width="23%" />
</div>
<br>
<p>You can save executable code on any server in the .dex file format and run it using this application. </p>
<p>The .dex format can be obtained from files in the .class format or from files in the .jar format (when compiling a project in Android Studio, the .dex files of all classes are saved in the / app / build / intermediates / project_dex_archive / debug / out / com / ... ( userName) / runningfromdexfile).</p>
<p>At the bottom of this screen is a ConstraintLayout named myLayout, you can refer to it from code and output information to it, for example you can generate programmatically TextView and place it on this myLayout.</p>
<p>You can view the contents of the .dex file using jadx app or similar</p>
<p>If you have any questions, you can write to me at <a href="mailto:roman.kryvolapov@gmail.com">roman.kryvolapov@gmail.com</a></p>

<br>
<br>

<div align="center">
<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_1.jpg" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_2.jpg" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_3.jpg" width="23%" />&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot_4.jpg" width="23%" />
</div>
<br>

<p>Вы можете сохранять исполняемый код на любом сервере в формате файла .dex и запускать его с помощью этого приложения. </p>
<p>Формат .dex  можно получить из файлов формата .class или из файлов формата .jar (при компилировании проекта в Android Studio файлы .dex всех классов сохраняются в папку /app/build/intermediates/project_dex_archive/debug/out/com/...(userName)/runningfromdexfile). </p>
<p>В нижней части этого экрана находится ConstraintLayout с именем myLayout, вы можете обращаться к нему из кода и выводить на него информацию, например вы можете сгенерировать программно TextView и поместить его на этот myLayout. </p>
<p>Вы можете просмотреть содержимое .dex файла с помощью приложения jadx или подобных</p>
<p>Если у вас есть какие либо вопросы, вы можете написать мне на почту <a href="mailto:roman.kryvolapov@gmail.com">roman.kryvolapov@gmail.com</a></p>
<br>
<br>

<p>Example of DEX</p>
<br>
<p>package com.romankryvolapov.runningfromdexfile;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;public class Test {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void test() {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MainActivity.myTextView.setText("123");</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MainActivity.myLayout.removeAllViews();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MainActivity.myLayout.addView(MainActivity.myTextView);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;}</p>
<p>}</p>




