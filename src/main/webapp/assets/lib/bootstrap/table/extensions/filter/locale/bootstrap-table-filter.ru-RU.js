/**
 * Bootstrap Table Filter Russian translation
 * Author: Lebedyuk Ilya (laplasianin@gmail.com)
 */
(function ($) {
    'use strict';

    $.extend($.fn.bootstrapTableFilter.defaults, {
        formatRemoveFiltersMessage: function () {
            return 'Очистить';
        },
        formatSearchMessage: function () {
            return 'Поиск';
        }
    });
    $.extend($.fn.bootstrapTableFilter.defaults, {
        formatRemoveFiltersMessage: function () {
            return 'Очистить';
        },
        formatSearchMessage: function () {
            return 'Поиск';
        }
    });

    $.fn.bootstrapTableFilter.filterSources.range.rows[0].i18n.msg = 'Меньше';
    $.fn.bootstrapTableFilter.filterSources.range.rows[1].i18n.msg = 'Больше';
    $.fn.bootstrapTableFilter.filterSources.range.rows[2].i18n.msg = 'Больше нуля';

    $.fn.bootstrapTableFilter.filterSources.search.rows[0].i18n.msg = 'Равно';
    $.fn.bootstrapTableFilter.filterSources.search.rows[1].i18n.msg = 'Не равно';
    $.fn.bootstrapTableFilter.filterSources.search.rows[2].i18n.msg = 'Содержит';
    $.fn.bootstrapTableFilter.filterSources.search.rows[3].i18n.msg = 'Нет значения';
    $.fn.bootstrapTableFilter.filterSources.search.rows[4].i18n.msg = 'Есть значение';

})(jQuery);