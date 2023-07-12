# Pin npm packages by running ./bin/importmap

pin "application", preload: true
pin "actioncable", to: "https://ga.jspm.io/npm:actioncable@5.2.8-1/lib/assets/compiled/action_cable.js"
pin "@rails/ujs", to: "https://ga.jspm.io/npm:@rails/ujs@7.0.6/lib/assets/compiled/rails-ujs.js"
pin "@hotwired/stimulus", to: "stimulus.min.js", preload: true
pin "@hotwired/stimulus-loading", to: "stimulus-loading.js", preload: true
pin_all_from "app/javascript/controllers", under: "controllers"
