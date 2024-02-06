const colors = require("tailwindcss/colors");

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "node_modules/flowbite-react/lib/esm/**/*.js",
  ],
  theme: {
    colors: {
      transparent: "transparent",
      current: "currentColor",
      blue: {
        DEFAULT: "rgba(0, 106, 255, 1)",
        10: "rgba(0, 85, 204, 1)",
        20: "rgba(0, 95, 229, 1)",
        30: "rgba(204, 225, 255, 1)",
        40: "rgba(229, 240, 255, 1)",
      },
      green: "#00B23B",
      red: "#CC0023",
      orange: "#FF9F40",
      gray: {
        10: "rgba(0, 0, 0, 0.9)",
        20: "rgba(0, 0, 0, 0.3)",
        30: "rgba(0, 0, 0, 0.15)",
        40: "rgba(0, 0, 0, 0.05)",
        50: "rgba(0, 0, 0, 0.03)",
      },
      black: "rgba(0, 0, 0, 0.9)",
      white: "rgba(255, 255, 255, 1)",
      emerald: colors.emerald,
      indigo: colors.indigo,
      yellow: colors.yellow,
    },
    boxShadow: {
      sm: "0px 0px 4px 0px rgba(0, 0, 0, 0.1), 0px 1px 2px 0px rgba(0, 0, 0, 0.2)",
      md: "0px 2px 16px 0px rgba(0, 0, 0, 0.1), 0px 4px 8px 0px rgba(0, 0, 0, 0.1)",
      lg: "0px 4px 32px 0px rgba(0, 0, 0, 0.1), 0px 8px 16px 0px rgba(0, 0, 0, 0.1)",
    },
  },
  // plugins: [require("flowbite/plugin")],
};
