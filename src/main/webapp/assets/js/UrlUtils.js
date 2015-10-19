var UrlUtils = UrlUtils || {
      getUrl : function(urlId) {
          return $('form#base-urls #' + urlId).val();
      }
};
