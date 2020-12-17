
Pod::Spec.new do |s|
  s.name         = "RNTrustVisionRnsdkFramework"
  s.version      = "1.0.15-OCB"
  s.summary      = "RNTrustVisionRnsdkFramework"
  s.description  = <<-DESC
                  RNTrustVisionRnsdkFramework
                   DESC
  s.homepage     = "https://github.com/tsocial/TrustVisionRNSDKFramework"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNTrustVisionRnsdkFramework.git", :tag => "release" }
#  s.source_files  = "RNTrustVisionRnsdkFramework/**/*.{h,m}"
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.resources = ['ios/Frameworks/TrustVisionSDK.framework/TrustVisionSDK.bundle']
  s.vendored_frameworks = [
      'ios/Frameworks/TrustVisionSDK.framework'
  ]

  s.dependency "React"
  s.dependency 'TensorFlowLiteSwift', '~> 2.2.0'

end
