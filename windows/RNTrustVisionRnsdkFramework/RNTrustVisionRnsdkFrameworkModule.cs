using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Trust.Vision.Rnsdk.Framework.RNTrustVisionRnsdkFramework
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNTrustVisionRnsdkFrameworkModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNTrustVisionRnsdkFrameworkModule"/>.
        /// </summary>
        internal RNTrustVisionRnsdkFrameworkModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNTrustVisionRnsdkFramework";
            }
        }
    }
}
