# Linux_Terminal_on_Java
- **Эмуляция файловой системы.** Классы: директория, файл (наверно, директория должна наследоваться от файла). Должна поддерживаться концепция текущей директории, относительно которой возможны все операции. Для упрощения задачи будем считать, что файлы только текстовые и записать/прочитать файл можно только целиком (также возможна дозапись в файл).	
- Реализовать простейший эмулятор командной строки с командами: cd (перемещение по файловой системе), ls (список файлов), mkdir (создание директории), rm (удаление), echo (печать строки), cat (печать файла) и tree (печать содержимого директории в виде дерева), а также мозможностью перенаправления вывода в файл с помощью выражений ">" и ">>".	
   Пример возможной сессии:
   ` mkdir "Первая папка"
     cd "Первая папка"
     echo "строка 1" > ./file1.txt
     echo "строка 2" >> ./file1.txt
     cat file1.txt >../copy1.txt
     ls / >ls.txt
     cd ..
     tree . `
- Что конкретно делает каждая команда – поискать в Интернете (естественно, требуется реализации в самом простейшем виде, без ключей и т.п.).		
- В результате чего должна получиться файловая система:	
   /
    Первая папка/
      file1.txt
      ls.txt
      copy1.txt
Команда `tree` примерно это и должна напечатать.
