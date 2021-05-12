Rails.application.routes.draw do
  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  get "/", to: "welcome#index", as: 'welcome'
  get '/auth/:provider/callback', to: 'sessions#create'
  resources :sites, only: [:index, :show] do 
    resources :snippets, only: [:create, :destroy]
  end
  
end
