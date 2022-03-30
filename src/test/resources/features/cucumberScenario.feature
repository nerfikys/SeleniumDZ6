#language: ru
@all
Функционал: Прощёлкивание озона

  @Test
  Сценарий: Выбор айфонов
    * Проверка что эта страница является домашней
    * Отправка "iphone" в строку поиска
    * Нажатие на кнопку поиска
    * Установка значений фильтров:
      | Цена            | до   | 150000 |
      | Высокий рейтинг | none | click  |
      | Беспроводные    | NFC  | click  |
    * Выбор 8 чётных продуктов
    * Нажатие на корзину
    * Обязательная проверка при первом входе на наличие всплывающего окна и его закрытие
    * Проверка что всё из списка присутствует в корзине
    * Проверяем текст "Ваша корзина N товаров"
    * Удалить всё из корзины
    * Проверка что корзина пуста
    * Распечатка отчёта

  @Test
  Сценарий: Выбор наушников
    * Проверка что эта страница является домашней
    * Отправка "беспроводные наушники" в строку поиска
    * Нажатие на кнопку поиска
    * Установка значений фильтров:
      | Цена            | до      | 50000 |
      | Бренды          | Beats   | click |
      | Бренды          | Samsung | click |
      | Бренды          | Xiaomi  | click |
      | Высокий рейтинг | none    | click |
    * Выбор всех нечётных продуктов
    * Нажатие на корзину
    * Обязательная проверка при первом входе на наличие всплывающего окна и его закрытие
    * Проверка что всё из списка присутствует в корзине
    * Проверяем текст "Ваша корзина N товаров"
    * Удалить всё из корзины
    * Проверка что корзина пуста
    * Распечатка отчёта