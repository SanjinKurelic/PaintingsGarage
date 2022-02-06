import AddToCartDialog from './AddToCartDialog'
import ChangePlanDialog from './ChangePlanDialog'
import DeleteDialog from './DeleteDialog'
import DownloadDialog from './DownloadDialog'
import EditDialog from './EditDialog'
import UploadDialog from './UploadDialog'
import './dialog.scss'

const DialogType = {
  ADD_TO_CART: 0,
  CHANGE_PLAN: 1,
  DELETE: 2,
  DOWNLOAD: 3,
  EDIT: 4,
  UPLOAD: 5,
}

export const getDialog = (type, data) => {
  switch (type) {
    case DialogType.ADD_TO_CART:
      return (<AddToCartDialog image={data}/>)
    case DialogType.CHANGE_PLAN:
      return (<ChangePlanDialog callback={data}/>)
    case DialogType.DELETE:
      return (<DeleteDialog image={data}/>)
    case DialogType.DOWNLOAD:
      return (<DownloadDialog image={data}/>)
    case DialogType.EDIT:
      return (<EditDialog image={data}/>)
    case DialogType.UPLOAD:
      return (<UploadDialog callback={data}/>)
    default:
      return ''
  }
}

export {DialogType, AddToCartDialog, ChangePlanDialog, DeleteDialog, DownloadDialog, EditDialog}
