
<br>
<p>Вы можете сохранять исполняемый код на любом сервере в формате файла .dex и запускать его с помощью этого приложения. </p>
<p>Формат .dex  можно получить из файлов формата .class или из файлов формата .jar (при компилировании проекта в Android Studio файлы .dex всех классов сохраняются в папку /app/build/intermediates/project_dex_archive/debug/out/com/...(userName)/runningfromdexfile). </p>
<p>В нижней части этого экрана находится ConstraintLayout с именем myLayout, вы можете обращаться к нему из кода и выводить на него информацию, например вы можете сгенерировать программно TextView и поместить его на этот myLayout. По всем вопросам пишите на roman.kryvolapov@gmail.com</p>
<br>

<p>Если у вас есть какие либо вопросы, вы можете написать мне на почту <a href="mailto:roman.kryvolapov@gmail.com">roman.kryvolapov@gmail.com</a></p>
<br>
<br>

<p>You can save executable code on any server in the .dex file format and run it using this application. </p>
<p>The .dex format can be obtained from files in the .class format or from files in the .jar format (when compiling a project in Android Studio, the .dex files of all classes are saved in the / app / build / intermediates / project_dex_archive / debug / out / com / ... ( userName) / runningfromdexfile).</p>
<p>At the bottom of this screen is a ConstraintLayout named myLayout, you can refer to it from code and output information to it, for example you can generate programmatically TextView and place it on this myLayout. For all questions, write to roman.kryvolapov@gmail.com</p>

<br>
<p>If you have any questions, you can write to me at <a href="mailto:roman.kryvolapov@gmail.com">roman.kryvolapov@gmail.com</a></p>
<br>

<p>Example of DEX</p><br>
<p>package com.romankryvolapov.runningfromdexfile;</p>
<p>public class Test {</p>
<p>public void test() {</p>
<p>MainActivity.myTextView.setText("123");</p>
<p>MainActivity.myLayout.removeAllViews();</p>
<p>MainActivity.myLayout.addView(MainActivity.myTextView);</p>
<p>}}</p>


<br>
<div align="center">
<img src="https://raw.githubusercontent.com/RomanKryvolapov/Java-and-Android/master/RunningfromDEXfile%20-%202020%20-%20Android/Screenshot.png" width="30%" />
</div>
<br>
