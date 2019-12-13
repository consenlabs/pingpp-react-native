require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = package['name']
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']

  s.authors      = package['author']
  s.homepage     = package['homepage']
  s.platform     = :ios, "9.0"

  s.source       = { :git => "https://git.coding.net/pingplusplus/pingpp-react-native.git", :tag => "#{s.version}" }
  s.source_files  = "ios/**/*.{h,m}"

  s.dependency 'React'

  s.dependency 'Pingpp/Alipay'
  s.dependency 'Pingpp/Wx'
  s.dependency 'Pingpp/UnionPay'
  s.dependency 'Pingpp/QQWallet'
  s.dependency 'Pingpp/CmbWallet'

end