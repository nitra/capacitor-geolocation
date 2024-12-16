Pod::Spec.new do |spec|
  spec.name                   = 'GeolocationLib'
  spec.version                = '1.0.0'

  spec.summary                = 'The `GeolocationLib` is a template library.'
  spec.description            = <<-DESC
  The `PingDemoLib` is a template library.
  
  The `PingDemoLib` structure provides the main feature of the Library:
  - ping: A simple echo function that returns the input string.
  DESC

  spec.homepage               = 'https://github.com/OutSystems/GeolocationLib-iOS'
  spec.license                = { :type => 'MIT', :file => 'LICENSE' }
  spec.author                 = { 'OutSystems Mobile Ecosystem' => 'rd.mobileecosystem.team@outsystems.com' }
  
  spec.source                 = { :http => "https://github.com/OutSystems/PingDemoLib-iOS/releases/download/#{spec.version}/DemoLib.zip", :type => "zip" }
  spec.source_files           = 'PingDemoLib/**/*.{swift,h,m,c,cc,mm,cpp}'
  spec.vendored_frameworks    = "PingDemoLib.xcframework"

  spec.ios.deployment_target  = '13.0'
  spec.swift_versions         = ['5.0', '5.1']
end