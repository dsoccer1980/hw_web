var webpack = require('webpack');
var path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');


module.exports = {
    entry: './src/index.js',
    output: {
        path: __dirname,
        filename: './src/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets:  ['@babel/preset-env',
                            '@babel/react',{
                                'plugins': ['@babel/plugin-proposal-class-properties']}]
                        ,
                        babelrcRoots: ['.', '../']
                    }
                }]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)$/,
                use: 'url-loader?limit=100000'
            }
        ]
    },
    devServer: {
        contentBase: DIST_FOLDER,
   port: 8888,
   // Send API requests on localhost to API server get around CORS.
   // proxy: {
   //    '/author': {
   //       target: {
   //          host: "192.168.0.101",
   //          protocol: 'http:',
   //          port: 8080
   //       },
   //       pathRewrite: {
   //          '^/author': ''
   //       }
   //    }
   // },

        headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
            "Access-Control-Allow-Headers": "X-Requested-With, content-type, Authorization"
        }
    },

};
